package com.aayush.rate_limiter.service.limiter;

import com.aayush.rate_limiter.model.RateLimitResponse;

public interface RateLimiter {

    RateLimitResponse checkLimit(String clientId, String endpoint);
}
