package com.qoobot.agent.core.hybrid;

import com.qoobot.agent.core.springai.AIEmbeddingService;
import com.qoobot.agent.core.springai.SpringAIRAGService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 混合 RAG 服务 - 支持多框架检索增强生成
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HybridRAGService {

    private final HybridAIService hybridAIService;
    private final SpringAIRAGService springAIRAGService;
    private final AIFrameworkSelector frameworkSelector;
    private final AIFrameworkMetrics metrics;

    // LangChain4J 组件（需要注入）
    private EmbeddingModel langchain4jEmbeddingModel;
    private EmbeddingStore<TextSegment> langchain4jEmbeddingStore;

    /**
     * 检索并生成
     */
    public String retrieveAndGenerate(String question, String agentId) {
        AIFramework framework = frameworkSelector.selectFramework(agentId);

        return switch (framework) {
            case LANGCHAIN4J -> retrieveAndGenerateWithLangChain4J(question, agentId);
            case SPRING_AI, SPRING_AI_ALIBABA -> springAIRAGService.retrieveAndGenerate(question, agentId);
        };
    }

    /**
     * 使用 LangChain4J 的 RAG
     */
    private String retrieveAndGenerateWithLangChain4J(String question, String agentId) {
        AIFrameworkMetrics.RequestContext context = metrics.recordRequestStart(AIFramework.LANGCHAIN4J);

        try {
            // 1. 检索相关文档
            List<TextSegment> relevantSegments = retrieveWithLangChain4J(question, agentId);

            // 2. 构建上下文
            String contextStr = buildContextFromSegments(relevantSegments);

            // 3. 生成回答
            ChatLanguageModel model = getLangChain4JChatModel();
            List<ChatMessage> messages = List.of(
                    new SystemMessage("你是一个专业的AI助手。请基于以下上下文信息回答用户问题。"),
                    new UserMessage("上下文信息：\n" + contextStr + "\n\n用户问题：" + question)
            );

            String response = model.generate(messages);

            metrics.recordRequestSuccess(context,
                    estimateTokenCount(question + contextStr),
                    estimateTokenCount(response));

            log.info("LangChain4J RAG completed - Response length: {}", response.length());
            return response;

        } catch (Exception e) {
            metrics.recordRequestFailure(context, e.getMessage());

            // Fallback 到 Spring AI
            log.warn("LangChain4J RAG failed, fallback to Spring AI: {}", e.getMessage());
            return springAIRAGService.retrieveAndGenerate(question, agentId);
        }
    }

    /**
     * 使用 LangChain4J 检索
     */
    private List<TextSegment> retrieveWithLangChain4J(String question, String agentId) {
        ContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingModel(langchain4jEmbeddingModel)
                .embeddingStore(langchain4jEmbeddingStore)
                .maxResults(5)
                .minScore(0.7)
                .build();

        List<EmbeddingMatch<TextSegment>> matches = retriever.retrieve(
                dev.langchain4j.data.message.UserMessage.from(question)
        );

        return matches.stream()
                .filter(match -> match.score() >= 0.7)
                .map(EmbeddingMatch::content)
                .collect(Collectors.toList());
    }

    /**
     * 混合检索 - 使用多个框架检索后聚合
     */
    public String hybridRetrieveAndGenerate(String question, String agentId, int minSources) {
        log.info("Hybrid RAG - Question: {}, Agent: {}, Min Sources: {}",
                question, agentId, minSources);

        // 1. 使用 Spring AI 检索
        List<Document> springAiDocs = springAIRAGService.similaritySearch(
                question, agentId, 5);

        // 2. 使用 LangChain4J 检索
        List<TextSegment> langChain4jSegments = retrieveWithLangChain4J(question, agentId);

        // 3. 聚合检索结果
        String aggregatedContext = aggregateContexts(springAiDocs, langChain4jSegments);

        // 4. 生成回答
        return hybridAIService.chatWithSystem(
                "你是一个专业的AI助手。请基于以下多来源的上下文信息回答用户问题。",
                "上下文信息：\n" + aggregatedContext + "\n\n用户问题：" + question
        );
    }

    /**
     * 多框架验证 - 使用多个框架生成答案并比较
     */
    public String retrieveAndGenerateWithValidation(String question, String agentId) {
        log.info("RAG with Validation - Question: {}, Agent: {}", question, agentId);

        // 1. 检索上下文
        List<Document> contextDocs = springAIRAGService.similaritySearch(
                question, agentId, 5);
        String context = buildContextFromDocuments(contextDocs);

        // 2. 使用不同框架生成答案
        String springAiAnswer = springAIRAGService.generateAnswer(question, context);
        String langChain4jAnswer = generateAnswerWithLangChain4J(question, context);

        // 3. 验证一致性
        double consistency = calculateConsistency(springAiAnswer, langChain4jAnswer);

        log.info("Answer consistency: {:.2f}", consistency);

        // 4. 选择最佳答案或返回聚合结果
        if (consistency > 0.8) {
            // 一致性高，选择 Spring AI 的答案
            return springAiAnswer;
        } else if (consistency > 0.5) {
            // 中等一致性，返回聚合答案
            return aggregateAnswers(springAiAnswer, langChain4jAnswer);
        } else {
            // 一致性低，返回带警告的答案
            return springAiAnswer + "\n\n[注意: 多框架答案一致性较低，建议人工核实]";
        }
    }

    /**
     * Agent 任务 RAG
     */
    public String executeAgentTaskWithRAG(String agentId, String taskType,
                                          String taskDescription, String toolsInfo) {
        AIFramework framework = frameworkSelector.selectFramework(agentId);

        return switch (framework) {
            case LANGCHAIN4J -> executeAgentTaskWithLangChain4J(
                    agentId, taskType, taskDescription, toolsInfo);
            case SPRING_AI, SPRING_AI_ALIBABA -> springAIRAGService.executeAgentTaskWithRAG(
                    agentId, taskType, taskDescription, toolsInfo);
        };
    }

    /**
     * 使用 LangChain4J 执行 Agent 任务
     */
    private String executeAgentTaskWithLangChain4J(String agentId, String taskType,
                                                   String taskDescription, String toolsInfo) {
        // 检索相关上下文
        List<TextSegment> relevantSegments = retrieveWithLangChain4J(taskDescription, agentId);
        String context = buildContextFromSegments(relevantSegments);

        // 构建任务提示
        String systemPrompt = String.format(
                "你是一个专业的%s智能助手。\n\n" +
                "可用工具：%s\n\n" +
                "相关上下文：%s",
                agentId, toolsInfo, context
        );

        ChatLanguageModel model = getLangChain4JChatModel();
        List<ChatMessage> messages = List.of(
                new SystemMessage(systemPrompt),
                new UserMessage(taskDescription)
        );

        return model.generate(messages);
    }

    /**
     * 索引文档 - 支持多框架索引
     */
    public void indexDocument(String agentId, String documentType,
                             String content, Map<String, Object> metadata) {
        // 使用 Spring AI 索引
        springAIRAGService.indexDocument(agentId, documentType, content, metadata);

        // 可选：同时使用 LangChain4J 索引
        if (langchain4jEmbeddingStore != null) {
            indexDocumentWithLangChain4J(agentId, documentType, content, metadata);
        }

        log.info("Document indexed with hybrid approach - Agent: {}, Type: {}", agentId, documentType);
    }

    /**
     * 使用 LangChain4J 索引文档
     */
    private void indexDocumentWithLangChain4J(String agentId, String documentType,
                                              String content, Map<String, Object> metadata) {
        TextSegment segment = TextSegment.from(content, dev.langchain4j.data.segment.Metadata.from(metadata));

        // 生成嵌入
        dev.langchain4j.model.output.Response<dev.langchain4j.data.embedding.Embedding> embeddingResponse =
                langchain4jEmbeddingModel.embed(content);

        // 存储到向量库
        String docId = agentId + "_" + documentType + "_" + System.currentTimeMillis();
        langchain4jEmbeddingStore.add(embeddingResponse.content(), segment, docId);
    }

    /**
     * 聚合上下文
     */
    private String aggregateContexts(List<Document> springAiDocs, List<TextSegment> langChain4jSegments) {
        StringBuilder aggregated = new StringBuilder();

        aggregated.append("[Spring AI 检索结果]\n");
        for (int i = 0; i < Math.min(springAiDocs.size(), 3); i++) {
            Document doc = springAiDocs.get(i);
            aggregated.append(i + 1).append(". ").append(doc.getContent()).append("\n");
        }

        aggregated.append("\n[LangChain4J 检索结果]\n");
        for (int i = 0; i < Math.min(langChain4jSegments.size(), 3); i++) {
            TextSegment segment = langChain4jSegments.get(i);
            aggregated.append(i + 1).append(". ").append(segment.text()).append("\n");
        }

        return aggregated.toString();
    }

    /**
     * 构建上下文（Spring AI）
     */
    private String buildContextFromDocuments(List<Document> documents) {
        return documents.stream()
                .map(doc -> "- " + doc.getContent())
                .collect(Collectors.joining("\n"));
    }

    /**
     * 构建上下文（LangChain4J）
     */
    private String buildContextFromSegments(List<TextSegment> segments) {
        return segments.stream()
                .map(segment -> "- " + segment.text())
                .collect(Collectors.joining("\n"));
    }

    /**
     * 生成答案（LangChain4J）
     */
    private String generateAnswerWithLangChain4J(String question, String context) {
        ChatLanguageModel model = getLangChain4JChatModel();
        List<ChatMessage> messages = List.of(
                new SystemMessage("基于以下上下文回答问题。"),
                new UserMessage("上下文：\n" + context + "\n\n问题：" + question)
        );
        return model.generate(messages);
    }

    /**
     * 计算答案一致性
     */
    private double calculateConsistency(String answer1, String answer2) {
        // 简单实现：基于关键词重叠度
        String words1 = answer1.toLowerCase().replaceAll("[^a-z\\u4e00-\\u9fa5\\s]", "");
        String words2 = answer2.toLowerCase().replaceAll("[^a-z\\u4e00-\\u9fa5\\s]", "");

        String[] wordArray1 = words1.split("\\s+");
        String[] wordArray2 = words2.split("\\s+");

        int common = 0;
        for (String word : wordArray1) {
            if (java.util.Arrays.asList(wordArray2).contains(word)) {
                common++;
            }
        }

        int totalUnique = wordArray1.length + wordArray2.length - common;
        return totalUnique > 0 ? (double) (2 * common) / totalUnique : 0.0;
    }

    /**
     * 聚合答案
     */
    private String aggregateAnswers(String answer1, String answer2) {
        return String.format(
                "[答案 1 (Spring AI)]\n%s\n\n[答案 2 (LangChain4J)]\n%s\n\n[综合说明]\n两个框架的答案在核心内容上基本一致。",
                answer1, answer2
        );
    }

    /**
     * 获取 LangChain4J ChatModel
     */
    private ChatLanguageModel getLangChain4JChatModel() {
        // 实现应该从配置或依赖注入获取
        return null; // 需要注入
    }

    /**
     * 估算 Token 数量
     */
    private int estimateTokenCount(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        return (int) Math.ceil(text.length() / 4.0);
    }

    /**
     * 相似度搜索（Spring AI）
     */
    private List<Document> similaritySearch(String question, String agentId, int topK) {
        // 需要通过 SpringAIRAGService 实现
        return List.of();
    }

    /**
     * 生成答案（Spring AI）
     */
    private String generateAnswer(String question, String context) {
        return hybridAIService.chatWithSystem(
                "基于以下上下文回答问题。",
                "上下文：\n" + context + "\n\n问题：" + question
        );
    }
}
