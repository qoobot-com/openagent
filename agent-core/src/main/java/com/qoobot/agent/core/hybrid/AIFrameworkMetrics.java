package com.qoobot.agent.core.hybrid;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * AI 框架性能指标收集器
 */
@Slf4j
@Component
public class AIFrameworkMetrics {

    /**
     * 请求计数
     */
    private final Map<AIFramework, LongAdder> requestCounts = new ConcurrentHashMap<>();

    /**
     * 成功计数
     */
    private final Map<AIFramework, LongAdder> successCounts = new ConcurrentHashMap<>();

    /**
     * 失败计数
     */
    private final Map<AIFramework, LongAdder> failureCounts = new ConcurrentHashMap<>();

    /**
     * 总响应时间（毫秒）
     */
    private final Map<AIFramework, AtomicLong> totalResponseTime = new ConcurrentHashMap<>();

    /**
     * 最大响应时间
     */
    private final Map<AIFramework, AtomicLong> maxResponseTime = new ConcurrentHashMap<>();

    /**
     * 最小响应时间
     */
    private final Map<AIFramework, AtomicLong> minResponseTime = new ConcurrentHashMap<>();

    /**
     * Token 使用统计
     */
    private final Map<AIFramework, TokenStats> tokenStats = new ConcurrentHashMap<>();

    public AIFrameworkMetrics() {
        for (AIFramework framework : AIFramework.values()) {
            requestCounts.put(framework, new LongAdder());
            successCounts.put(framework, new LongAdder());
            failureCounts.put(framework, new LongAdder());
            totalResponseTime.put(framework, new AtomicLong(0));
            maxResponseTime.put(framework, new AtomicLong(0));
            minResponseTime.put(framework, new AtomicLong(Long.MAX_VALUE));
            tokenStats.put(framework, new TokenStats());
        }
    }

    /**
     * 记录请求开始
     */
    public RequestContext recordRequestStart(AIFramework framework) {
        requestCounts.get(framework).increment();
        return new RequestContext(framework, System.currentTimeMillis());
    }

    /**
     * 记录请求成功
     */
    public void recordRequestSuccess(RequestContext context, int promptTokens, int completionTokens) {
        long responseTime = System.currentTimeMillis() - context.startTime;
        AIFramework framework = context.framework;

        successCounts.get(framework).increment();
        totalResponseTime.get(framework).addAndGet(responseTime);

        // 更新最大/最小响应时间
        updateResponseTime(framework, responseTime);

        // 更新 Token 统计
        TokenStats stats = tokenStats.get(framework);
        stats.promptTokens.add(promptTokens);
        stats.completionTokens.add(completionTokens);
        stats.totalTokens.add(promptTokens + completionTokens);

        log.debug("Request success - Framework: {}, Time: {}ms, Tokens: {}",
                framework, responseTime, promptTokens + completionTokens);
    }

    /**
     * 记录请求失败
     */
    public void recordRequestFailure(RequestContext context, String error) {
        long responseTime = System.currentTimeMillis() - context.startTime;
        AIFramework framework = context.framework;

        failureCounts.get(framework).increment();
        totalResponseTime.get(framework).addAndGet(responseTime);
        updateResponseTime(framework, responseTime);

        log.warn("Request failed - Framework: {}, Time: {}ms, Error: {}",
                framework, responseTime, error);
    }

    /**
     * 更新响应时间
     */
    private void updateResponseTime(AIFramework framework, long responseTime) {
        AtomicLong maxTime = maxResponseTime.get(framework);
        AtomicLong minTime = minResponseTime.get(framework);

        // 更新最大值
        long currentMax = maxTime.get();
        while (responseTime > currentMax && !maxTime.compareAndSet(currentMax, responseTime)) {
            currentMax = maxTime.get();
        }

        // 更新最小值
        long currentMin = minTime.get();
        while (responseTime < currentMin && !minTime.compareAndSet(currentMin, responseTime)) {
            currentMin = minTime.get();
        }
    }

    /**
     * 获取请求总数
     */
    public long getTotalRequests(AIFramework framework) {
        return requestCounts.get(framework).sum();
    }

    /**
     * 获取成功数
     */
    public long getSuccessCount(AIFramework framework) {
        return successCounts.get(framework).sum();
    }

    /**
     * 获取失败数
     */
    public long getFailureCount(AIFramework framework) {
        return failureCounts.get(framework).sum();
    }

    /**
     * 获取成功率
     */
    public double getSuccessRate(AIFramework framework) {
        long total = getTotalRequests(framework);
        if (total == 0) {
            return 0.0;
        }
        return (double) getSuccessCount(framework) / total;
    }

    /**
     * 获取平均响应时间
     */
    public double getAverageResponseTime(AIFramework framework) {
        long total = getTotalRequests(framework);
        if (total == 0) {
            return 0.0;
        }
        return (double) totalResponseTime.get(framework).get() / total;
    }

    /**
     * 获取最大响应时间
     */
    public long getMaxResponseTime(AIFramework framework) {
        return maxResponseTime.get(framework).get();
    }

    /**
     * 获取最小响应时间
     */
    public long getMinResponseTime(AIFramework framework) {
        long min = minResponseTime.get(framework).get();
        return min == Long.MAX_VALUE ? 0 : min;
    }

    /**
     * 获取 Token 统计
     */
    public TokenStats getTokenStats(AIFramework framework) {
        return tokenStats.get(framework);
    }

    /**
     * 获取所有框架的指标摘要
     */
    public FrameworkMetricsSummary getSummary() {
        Map<AIFramework, FrameworkMetrics> metricsMap = new ConcurrentHashMap<>();
        for (AIFramework framework : AIFramework.values()) {
            metricsMap.put(framework, FrameworkMetrics.builder()
                    .framework(framework)
                    .totalRequests(getTotalRequests(framework))
                    .successCount(getSuccessCount(framework))
                    .failureCount(getFailureCount(framework))
                    .successRate(getSuccessRate(framework))
                    .averageResponseTime(getAverageResponseTime(framework))
                    .maxResponseTime(getMaxResponseTime(framework))
                    .minResponseTime(getMinResponseTime(framework))
                    .totalTokens(getTokenStats(framework).totalTokens.sum())
                    .build());
        }
        return new FrameworkMetricsSummary(metricsMap);
    }

    /**
     * 重置指标
     */
    public void reset(AIFramework framework) {
        requestCounts.get(framework).reset();
        successCounts.get(framework).reset();
        failureCounts.get(framework).reset();
        totalResponseTime.get(framework).set(0);
        maxResponseTime.get(framework).set(0);
        minResponseTime.get(framework).set(Long.MAX_VALUE);

        TokenStats stats = tokenStats.get(framework);
        stats.promptTokens.reset();
        stats.completionTokens.reset();
        stats.totalTokens.reset();

        log.info("Metrics reset for framework: {}", framework);
    }

    /**
     * 重置所有指标
     */
    public void resetAll() {
        for (AIFramework framework : AIFramework.values()) {
            reset(framework);
        }
    }

    /**
     * 请求上下文
     */
    @Data
    public static class RequestContext {
        private final AIFramework framework;
        private final long startTime;
    }

    /**
     * Token 统计
     */
    @Data
    public static class TokenStats {
        private final LongAdder promptTokens = new LongAdder();
        private final LongAdder completionTokens = new LongAdder();
        private final LongAdder totalTokens = new LongAdder();
    }

    /**
     * 框架指标
     */
    @Data
    @lombok.Builder
    public static class FrameworkMetrics {
        private final AIFramework framework;
        private final long totalRequests;
        private final long successCount;
        private final long failureCount;
        private final double successRate;
        private final double averageResponseTime;
        private final long maxResponseTime;
        private final long minResponseTime;
        private final long totalTokens;
    }

    /**
     * 指标摘要
     */
    @Data
    public static class FrameworkMetricsSummary {
        private final Map<AIFramework, FrameworkMetrics> metrics;
    }
}
