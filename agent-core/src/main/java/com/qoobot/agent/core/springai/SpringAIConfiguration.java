package com.qoobot.agent.core.springai;

import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.document.TextSplitter;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Spring AI 配置类
 */
@Configuration
@Import({
        OpenAiAutoConfiguration.class,
        PgVectorStoreAutoConfiguration.class
})
public class SpringAIConfiguration {

    /**
     * 文本分割器
     */
    @Bean
    public TextSplitter textSplitter(SpringAIProperties properties) {
        return new TokenTextSplitter(
                properties.getRAG().getChunkSize(),
                properties.getRAG().getChunkOverlap(),
                5,
                10000,
                true
        );
    }

    /**
     * Prompt 模板 - 标准 RAG 提示
     */
    @Bean
    public PromptTemplate ragPromptTemplate() {
        return new PromptTemplate("""
                你是一个专业的AI助手。请基于以下上下文信息回答用户问题。

                上下文信息：
                {context}

                用户问题：
                {question}

                如果上下文中没有相关信息，请说明无法从提供的上下文中找到答案。
                """);
    }

    /**
     * Prompt 模板 - Agent 任务执行
     */
    @Bean
    public PromptTemplate agentTaskPromptTemplate() {
        return new PromptTemplate("""
                你是一个专业的{agent_type}智能助手。

                任务描述：
                {task_description}

                可用工具：
                {available_tools}

                相关上下文：
                {context}

                请使用适当的工具完成任务，并给出详细的分析和建议。
                """);
    }

    /**
     * Prompt 模板 - 总结任务
     */
    @Bean
    public PromptTemplate summaryPromptTemplate() {
        return new PromptTemplate("""
                请对以下内容进行简洁明了的总结：

                {content}

                要求：
                1. 保留关键信息
                2. 使用条理清晰的结构
                3. 字数控制在 {max_length} 字以内
                """);
    }
}
