package com.qoobot.agent.core.springai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * AI 向量嵌入服务 - 封装 Spring AI 向量操作能力
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AIEmbeddingService {

    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;
    private final TextSplitter textSplitter;
    private final SpringAIProperties properties;

    /**
     * 生成文本嵌入
     */
    public float[] embed(String text) {
        log.debug("Embedding text - Length: {}", text.length());
        EmbeddingResponse response = embeddingModel.embedForResponse(List.of(text));
        return response.getResults().get(0).getOutput();
    }

    /**
     * 批量生成嵌入
     */
    public List<float[]> embedBatch(List<String> texts) {
        log.info("Batch embedding - Count: {}", texts.size());
        EmbeddingResponse response = embeddingModel.embedForResponse(texts);
        return response.getResults().stream()
                .map(result -> result.getOutput())
                .toList();
    }

    /**
     * 添加文档到向量存储
     */
    public void addDocument(String id, String content, Map<String, Object> metadata) {
        log.info("Adding document to vector store - ID: {}", id);
        Document document = new Document(id, content, metadata);
        vectorStore.add(List.of(document));
    }

    /**
     * 批量添加文档
     */
    public void addDocuments(List<Document> documents) {
        log.info("Adding {} documents to vector store", documents.size());
        vectorStore.add(documents);
    }

    /**
     * 从文件加载文档并添加到向量存储
     */
    public void loadAndAddDocument(Resource resource, String agentId, String documentType) {
        log.info("Loading document from resource: {}", resource.getFilename());
        TextReader reader = new TextReader(resource);
        reader.setCustomMetadata(Map.of(
                "agent_id", agentId,
                "document_type", documentType
        ));

        List<Document> documents = reader.get();
        List<Document> chunks = textSplitter.apply(documents);

        chunks.forEach(chunk -> {
            Map<String, Object> metadata = chunk.getMetadata();
            metadata.put("chunk_id", metadata.getOrDefault("chunk_id", 0));
            metadata.put("total_chunks", chunks.size());
        });

        vectorStore.add(chunks);
        log.info("Loaded {} chunks from document", chunks.size());
    }

    /**
     * 相似度搜索
     */
    public List<Document> similaritySearch(String query, int topK) {
        log.debug("Similarity search - Query: {}, TopK: {}", query, topK);
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(topK)
                .similarityThreshold(properties.getRAG().getThreshold())
                .build();
        return vectorStore.similaritySearch(request);
    }

    /**
     * 按元数据过滤的相似度搜索
     */
    public List<Document> similaritySearchWithFilter(String query, int topK,
                                                       Map<String, Object> filter) {
        log.debug("Similarity search with filter - Query: {}, Filter: {}", query, filter);
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(topK)
                .similarityThreshold(properties.getRAG().getThreshold())
                .filterExpression(filter)
                .build();
        return vectorStore.similaritySearch(request);
    }

    /**
     * 删除文档
     */
    public void deleteDocument(String id) {
        log.info("Deleting document - ID: {}", id);
        vectorStore.delete(List.of(id));
    }

    /**
     * 批量删除文档
     */
    public void deleteDocuments(List<String> ids) {
        log.info("Deleting {} documents", ids.size());
        vectorStore.delete(ids);
    }

    /**
     * 计算文本相似度
     */
    public double calculateSimilarity(String text1, String text2) {
        float[] embedding1 = embed(text1);
        float[] embedding2 = embed(text2);
        return cosineSimilarity(embedding1, embedding2);
    }

    /**
     * 余弦相似度计算
     */
    private double cosineSimilarity(float[] vector1, float[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vectors must have same length");
        }

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            norm1 += Math.pow(vector1[i], 2);
            norm2 += Math.pow(vector2[i], 2);
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    /**
     * 文档分块
     */
    public List<Document> splitDocuments(List<Document> documents) {
        log.info("Splitting {} documents", documents.size());
        return textSplitter.apply(documents);
    }

    /**
     * 获取向量存储统计信息
     */
    public VectorStoreStats getStats() {
        // 实际实现需要根据具体的 VectorStore 实现
        return new VectorStoreStats(
                0,
                properties.getVectorStore().getDimension(),
                properties.getVectorStore().getIndexType(),
                properties.getVectorStore().getMetricType()
        );
    }

    /**
     * 向量存储统计信息
     */
    public record VectorStoreStats(
            int documentCount,
            int dimension,
            String indexType,
            String metricType
    ) {}
}
