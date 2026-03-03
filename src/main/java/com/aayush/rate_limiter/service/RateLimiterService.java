package com.aayush.rate_limiter.service;

import com.aayush.rate_limiter.service.limiter.FixedWindowLimiter;
import com.aayush.rate_limiter.service.limiter.TokenBucketLimiter;
import com.aayush.rate_limiter.model.RateLimitResponse;
import com.aayush.rate_limiter.service.limiter.RateLimiter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RateLimiterService {

    private final FixedWindowLimiter fixedWindowLimiter;
    private final TokenBucketLimiter tokenBucketLimiter;
    private final RateLimitMetrics metrics;

    @Value("${rate.limit.strategy}")
    private String strategy;

    public RateLimiterService(FixedWindowLimiter fixedWindowLimiter,
                              TokenBucketLimiter tokenBucketLimiter,
                              RateLimitMetrics metrics) {
        this.fixedWindowLimiter = fixedWindowLimiter;
        this.tokenBucketLimiter = tokenBucketLimiter;
        this.metrics = metrics;
    }

    public RateLimitResponse check(String clientId, String endpoint) {

        // 🔹 Count every incoming request
        metrics.incrementTotal();

        RateLimitResponse response;

        if ("fixed".equalsIgnoreCase(strategy)) {
            response = fixedWindowLimiter.checkLimit(clientId, endpoint);
        } else {
            response = tokenBucketLimiter.checkLimit(clientId, endpoint);
        }

        // 🔹 If request is blocked, increment blocked counter
        if (!response.isAllowed()) {
            metrics.incrementBlocked();
        }

        return response;
    }
}
