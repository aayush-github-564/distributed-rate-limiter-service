package com.aayush.rate_limiter.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class RateLimitMetrics {

    private final Counter totalRequests;
    private final Counter blockedRequests;

    public RateLimitMetrics(MeterRegistry registry) {
        this.totalRequests = registry.counter("rate_limiter.total_requests");
        this.blockedRequests = registry.counter("rate_limiter.blocked_requests");
    }

    public void incrementTotal() {
        totalRequests.increment();
    }

    public void incrementBlocked() {
        blockedRequests.increment();
    }
}