package com.qoobot.agent.core;

import com.pgvector.PGvector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PostgreSQL + pgvector 向量数据库操作类
 *
 * @author openagent
 * @since 1.0.0
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "agent.vector.enabled", havingValue = "true", matchIfMissing = true)
public class VectorDatabase {

    private final AgentConfig config;

    public VectorDatabase(AgentConfig config) {
        this.config = config;
    }

    /**
     * 初始化向量表
     */
    public void initializeVectorTable() {
        String sql = """
            CREATE EXTENSION IF NOT EXISTS vector;

            CREATE TABLE IF NOT EXISTS agent_data.embeddings (
                id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                agent_id VARCHAR(100) NOT NULL,
                embedding_id VARCHAR(255) NOT NULL,
                content TEXT,
                metadata JSONB,
                embedding vector(1536),
                created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
                updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
            );

            CREATE INDEX IF NOT EXISTS idx_embeddings_agent_id ON agent_data.embeddings(agent_id);
            CREATE INDEX IF NOT EXISTS idx_embeddings_vector
                ON agent_data.embeddings USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);
            CREATE INDEX IF NOT EXISTS idx_embeddings_metadata
                ON agent_data.embeddings USING GIN (metadata);
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            log.info("Vector table initialized successfully");

        } catch (SQLException e) {
            log.error("Failed to initialize vector table", e);
            throw new RuntimeException("Vector table initialization failed", e);
        }
    }

    /**
     * 插入向量数据
     */
    public void insertEmbedding(String agentId, String embeddingId, String content,
                               Map<String, Object> metadata, float[] embedding) {
        String sql = """
            INSERT INTO agent_data.embeddings (agent_id, embedding_id, content, metadata, embedding)
            VALUES (?, ?, ?, ?::jsonb, ?)
            ON CONFLICT (embedding_id)
            DO UPDATE SET
                content = EXCLUDED.content,
                metadata = EXCLUDED.metadata,
                embedding = EXCLUDED.embedding,
                updated_at = NOW()
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, agentId);
            pstmt.setString(2, embeddingId);
            pstmt.setString(3, content);
            pstmt.setString(4, toJson(metadata));
            pstmt.setObject(5, new PGvector(embedding));

            pstmt.executeUpdate();
            log.debug("Inserted embedding: {}", embeddingId);

        } catch (SQLException e) {
            log.error("Failed to insert embedding: {}", embeddingId, e);
            throw new RuntimeException("Embedding insertion failed", e);
        }
    }

    /**
     * 向量相似度搜索
     */
    public List<VectorSearchResult> searchSimilar(String agentId, float[] queryVector,
                                                  int limit, double threshold) {
        String sql = """
            SELECT
                id,
                embedding_id,
                content,
                metadata,
                1 - (embedding <=> ?) AS similarity
            FROM agent_data.embeddings
            WHERE agent_id = ?
              AND (1 - (embedding <=> ?)) >= ?
            ORDER BY embedding <=> ?
            LIMIT ?
            """;

        List<VectorSearchResult> results = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, new PGvector(queryVector));
            pstmt.setString(2, agentId);
            pstmt.setObject(3, new PGvector(queryVector));
            pstmt.setDouble(4, threshold);
            pstmt.setObject(5, new PGvector(queryVector));
            pstmt.setInt(6, limit);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                VectorSearchResult result = new VectorSearchResult();
                result.setId(rs.getString("id"));
                result.setEmbeddingId(rs.getString("embedding_id"));
                result.setContent(rs.getString("content"));
                result.setMetadata(toMap(rs.getString("metadata")));
                result.setSimilarity(rs.getDouble("similarity"));
                results.add(result);
            }

        } catch (SQLException e) {
            log.error("Vector search failed", e);
            throw new RuntimeException("Vector search failed", e);
        }

        return results;
    }

    /**
     * 批量插入向量数据
     */
    public void batchInsertEmbeddings(String agentId, List<EmbeddingData> embeddings) {
        String sql = """
            INSERT INTO agent_data.embeddings (agent_id, embedding_id, content, metadata, embedding)
            VALUES (?, ?, ?, ?::jsonb, ?)
            """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (EmbeddingData embedding : embeddings) {
                pstmt.setString(1, agentId);
                pstmt.setString(2, embedding.getEmbeddingId());
                pstmt.setString(3, embedding.getContent());
                pstmt.setString(4, toJson(embedding.getMetadata()));
                pstmt.setObject(5, new PGvector(embedding.getVector()));
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();

            log.info("Batch inserted {} embeddings", embeddings.size());

        } catch (SQLException e) {
            log.error("Batch insert failed", e);
            throw new RuntimeException("Batch insert failed", e);
        }
    }

    /**
     * 删除向量数据
     */
    public void deleteEmbedding(String embeddingId) {
        String sql = "DELETE FROM agent_data.embeddings WHERE embedding_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, embeddingId);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                log.debug("Deleted embedding: {}", embeddingId);
            }

        } catch (SQLException e) {
            log.error("Failed to delete embedding: {}", embeddingId, e);
            throw new RuntimeException("Embedding deletion failed", e);
        }
    }

    /**
     * 获取数据库连接
     */
    private Connection getConnection() throws SQLException {
        String url = config.getApiConfig().getDatabaseUrl();
        String username = config.getApiConfig().getDatabaseUsername();
        String password = config.getApiConfig().getDatabasePassword();

        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 将Map转换为JSON字符串
     */
    private String toJson(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            sb.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof String) {
                sb.append("\"").append(value).append("\"");
            } else {
                sb.append(value);
            }
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * 将JSON字符串转换为Map
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> toMap(String json) {
        // 简单实现，实际项目中应使用Jackson或其他JSON库
        return Map.of(); // TODO: 实现JSON解析
    }

    /**
     * 向量搜索结果
     */
    public static class VectorSearchResult {
        private String id;
        private String embeddingId;
        private String content;
        private Map<String, Object> metadata;
        private double similarity;

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getEmbeddingId() { return embeddingId; }
        public void setEmbeddingId(String embeddingId) { this.embeddingId = embeddingId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
        public double getSimilarity() { return similarity; }
        public void setSimilarity(double similarity) { this.similarity = similarity; }
    }

    /**
     * 向量数据
     */
    public static class EmbeddingData {
        private String embeddingId;
        private String content;
        private Map<String, Object> metadata;
        private float[] vector;

        // Getters and Setters
        public String getEmbeddingId() { return embeddingId; }
        public void setEmbeddingId(String embeddingId) { this.embeddingId = embeddingId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public Map<String, Object> getMetadata() { return metadata; }
        public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
        public float[] getVector() { return vector; }
        public void setVector(float[] vector) { this.vector = vector; }
    }
}
