package com.aayush.rate_limiter.service;

import com.aayush.rate_limiter.model.RateLimitResponse;
import com.aayush.rate_limiter.service.limiter.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final RateLimiter rateLimiter;

    public RateLimiterService(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public RateLimitResponse check(String clientId, String endpoint) {
        return rateLimiter.checkLimit(clientId, endpoint);
    }
}
