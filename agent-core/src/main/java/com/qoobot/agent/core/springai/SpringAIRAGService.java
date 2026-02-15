package com.qoobot.agent.core.springai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Spring AI RAG 服务 - 检索增强生成
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SpringAIRAGService {

    private final AIChatService chatService;
    private final AIEmbeddingService embeddingService;
    private final VectorStore vectorStore;
    private final SpringAIProperties properties;

    /**
     * 标准 RAG 流程
     */
    public String retrieveAndGenerate(String question, String agentId) {
        log.info("RAG Pipeline - Question: {}, Agent: {}", question, agentId);

        // 1. 检索相关文档
        List<Document> relevantDocs = embeddingService.similaritySearchWithFilter(
                question,
                properties.getRAG().getTopK(),
                Map.of("agent_id", agentId)
        );

        if (relevantDocs.isEmpty()) {
            log.warn("No relevant documents found for question: {}", question);
            return "抱歉，未找到相关信息。";
        }

        // 2. 构建上下文
        String context = buildContext(relevantDocs);
        log.debug("Built context with {} documents", relevantDocs.size());

        // 3. 生成回答
        String response = chatService.chatWithRAG(question, context);

        log.info("RAG Pipeline completed - Answer length: {}", response.length());
        return response;
    }

    /**
     * 带 Rerank 的 RAG
     */
    public String retrieveAndGenerateWithRerank(String question, String agentId, int topK) {
        log.info("RAG with Rerank - Question: {}, Agent: {}, TopK: {}", question, agentId, topK);

        // 1. 检索更多文档
        List<Document> candidates = embeddingService.similaritySearchWithFilter(
                question,
                topK * 2,
                Map.of("agent_id", agentId)
        );

        if (candidates.isEmpty()) {
            return "抱歉，未找到相关信息。";
        }

        // 2. Rerank (简化版：基于相似度分数)
        List<Document> rerankedDocs = rerankDocuments(question, candidates, topK);

        // 3. 构建上下文并生成回答
        String context = buildContext(rerankedDocs);
        return chatService.chatWithRAG(question, context);
    }

    /**
     * 多来源 RAG
     */
    public String retrieveAndGenerateMultiSource(String question,
                                                    Map<String, Object> filters) {
        log.info("Multi-source RAG - Question: {}, Filters: {}", question, filters);

        List<Document> relevantDocs = embeddingService.similaritySearchWithFilter(
                question,
                properties.getRAG().getTopK(),
                filters
        );

        if (relevantDocs.isEmpty()) {
            return "抱歉，未找到相关信息。";
        }

        String context = buildContext(relevantDocs);
        return chatService.chatWithRAG(question, context);
    }

    /**
     * Agent 任务 RAG
     */
    public String executeAgentTaskWithRAG(String agentId, String taskType,
                                            String taskDescription, String toolsInfo) {
        log.info("Agent Task RAG - Agent: {}, Type: {}, Task: {}", agentId, taskType, taskDescription);

        // 1. 检索相关上下文
        List<Document> contextDocs = embeddingService.similaritySearchWithFilter(
                taskDescription,
                properties.getRAG().getTopK(),
                Map.of("agent_id", agentId, "task_type", taskType)
        );

        // 2. 构建上下文
        String context = contextDocs.isEmpty() ?
                "无相关历史经验。" :
                buildContext(contextDocs);

        // 3. 执行任务
        return chatService.executeAgentTask(
                agentId,
                taskDescription,
                toolsInfo,
                context
        );
    }

    /**
     * 文档索引
     */
    public void indexDocument(String agentId, String documentType,
                               String content, Map<String, Object> additionalMetadata) {
        log.info("Indexing document - Agent: {}, Type: {}", agentId, documentType);

        Map<String, Object> metadata = Map.of(
                "agent_id", agentId,
                "document_type", documentType,
                "indexed_at", System.currentTimeMillis()
        );

        if (additionalMetadata != null) {
            metadata.putAll(additionalMetadata);
        }

        String docId = generateDocumentId(agentId, documentType);
        embeddingService.addDocument(docId, content, metadata);

        log.info("Document indexed successfully - ID: {}", docId);
    }

    /**
     * 批量文档索引
     */
    public void batchIndexDocuments(List<DocumentData> documents) {
        log.info("Batch indexing {} documents", documents.size());

        documents.forEach(doc -> {
            String docId = generateDocumentId(doc.agentId(), doc.documentType());
            Map<String, Object> metadata = Map.of(
                    "agent_id", doc.agentId(),
                    "document_type", doc.documentType(),
                    "indexed_at", System.currentTimeMillis()
            );
            if (doc.additionalMetadata() != null) {
                metadata.putAll(doc.additionalMetadata());
            }

            embeddingService.addDocument(docId, doc.content(), metadata);
        });

        log.info("Batch indexing completed");
    }

    /**
     * 知识库更新
     */
    public void updateKnowledgeBase(String agentId, String documentType,
                                      String content, Map<String, Object> metadata) {
        String docId = generateDocumentId(agentId, documentType);
        embeddingService.deleteDocument(docId);
        indexDocument(agentId, documentType, content, metadata);
        log.info("Knowledge base updated - ID: {}", docId);
    }

    /**
     * 文档查询
     */
    public List<Document> queryDocuments(String agentId, String documentType) {
        log.debug("Querying documents - Agent: {}, Type: {}", agentId, documentType);
        return embeddingService.similaritySearchWithFilter(
                "",
                100,
                Map.of("agent_id", agentId, "document_type", documentType)
        );
    }

    /**
     * 构建上下文字符串
     */
    private String buildContext(List<Document> documents) {
        StringBuilder context = new StringBuilder();
        for (int i = 0; i < documents.size(); i++) {
            Document doc = documents.get(i);
            context.append("[文档 ").append(i + 1).append("]\n");
            context.append("内容: ").append(doc.getContent()).append("\n");
            if (!doc.getMetadata().isEmpty()) {
                context.append("元数据: ").append(doc.getMetadata()).append("\n");
            }
            context.append("\n");
        }
        return context.toString();
    }

    /**
     * 文档重排序 (简化版)
     */
    private List<Document> rerankDocuments(String query, List<Document> documents, int topK) {
        // 实际实现可以使用专门的 rerank 模型
        // 这里简化为直接按相似度排序
        return documents.stream()
                .limit(topK)
                .toList();
    }

    /**
     * 生成文档 ID
     */
    private String generateDocumentId(String agentId, String documentType) {
        return String.format("%s_%s_%d", agentId, documentType, System.currentTimeMillis());
    }

    /**
     * 文档数据记录
     */
    public record DocumentData(
            String agentId,
            String documentType,
            String content,
            Map<String, Object> additionalMetadata
    ) {}
}
