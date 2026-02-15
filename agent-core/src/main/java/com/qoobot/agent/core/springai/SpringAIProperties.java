package com.qoobot.agent.core.springai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Spring AI 配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.ai")
public class SpringAIProperties {

    /**
     * OpenAI 配置
     */
    private OpenAI openAI = new OpenAI();

    /**
     * 阿里云 DashScope 配置
     */
    private DashScope dashScope = new DashScope();

    /**
     * 向量存储配置
     */
    private VectorStore vectorStore = new VectorStore();

    /**
     * RAG 配置
     */
    private RAG rag = new RAG();

    @Data
    public static class OpenAI {
        /**
         * API Key
         */
        private String apiKey;

        /**
         * Base URL
         */
        private String baseUrl = "https://api.openai.com/v1";

        /**
         * Chat 模型
         */
        private ChatOptions chat = new ChatOptions();

        /**
         * Embedding 模型
         */
        private String embeddingModel = "text-embedding-3-small";

        /**
         * 是否启用
         */
        private boolean enabled = true;
    }

    @Data
    public static class ChatOptions {
        /**
         * 模型名称
         */
        private String model = "gpt-4o-mini";

        /**
         * 温度 (0-2)
         */
        private Double temperature = 0.7;

        /**
         * 最大 Token 数
         */
        private Integer maxTokens = 2000;

        /**
         * Top P (0-1)
         */
        private Double topP = 1.0;

        /**
         * 频率惩罚 (-2.0 - 2.0)
         */
        private Double frequencyPenalty = 0.0;

        /**
         * 存在惩罚 (-2.0 - 2.0)
         */
        private Double presencePenalty = 0.0;
    }

    @Data
    public static class DashScope {
        /**
         * API Key
         */
        private String apiKey;

        /**
         * Base URL
         */
        private String baseUrl = "https://dashscope.aliyuncs.com/compatible-mode/v1";

        /**
         * Chat 模型
         */
        private ChatOptions chat = new ChatOptions();

        /**
         * Embedding 模型
         */
        private String embeddingModel = "text-embedding-v3";

        /**
         * 是否启用
         */
        private boolean enabled = false;
    }

    @Data
    public static class DashScopeChatOptions {
        /**
         * 模型名称
         */
        private String model = "qwen-plus";

        /**
         * 温度
         */
        private Double temperature = 0.7;

        /**
         * 最大 Token 数
         */
        private Integer maxTokens = 2000;

        /**
         * Top P
         */
        private Double topP = 1.0;
    }

    @Data
    public static class VectorStore {
        /**
         * 类型: pgvector, milvus
         */
        private String type = "pgvector";

        /**
         * 维度
         */
        private Integer dimension = 1536;

        /**
         * 索引类型
         */
        private String indexType = "ivfflat";

        /**
         * 距离度量: cosine, euclidean, inner_product
         */
        private String metricType = "cosine";

        /**
         * IVFFlat 列表数
         */
        private Integer indexLists = 100;
    }

    @Data
    public static class RAG {
        /**
         * Top K 检索数量
         */
        private Integer topK = 5;

        /**
         * 相似度阈值
         */
        private Double threshold = 0.7;

        /**
         * 文档分块大小
         */
        private Integer chunkSize = 1000;

        /**
         * 分块重叠大小
         */
        private Integer chunkOverlap = 200;

        /**
         * 是否启用 RAG
         */
        private boolean enabled = true;
    }
}
