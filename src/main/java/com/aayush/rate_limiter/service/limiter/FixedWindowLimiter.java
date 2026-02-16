package com.aayush.rate_limiter.service.limiter;

import com.aayush.rate_limiter.model.RateLimitResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
public class FixedWindowLimiter implements RateLimiter {

    @Value("${ratelimiter.fixed.limit}")
    private long limit;

    @Value("${ratelimiter.fixed.window-seconds}")
    private long windowSizeSeconds;

    private final StringRedisTemplate redisTemplate;

    public FixedWindowLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public RateLimitResponse checkLimit(String clientId, String endpoint) {

        long currentWindow = Instant.now().getEpochSecond() / windowSizeSeconds;

        String key = "rate_limit:" + clientId + ":" + endpoint + ":" + currentWindow;

        Long currentCount = redisTemplate.opsForValue().increment(key);

        if (currentCount == 1) {
            redisTemplate.expire(key, windowSizeSeconds, TimeUnit.SECONDS);
        }

        boolean allowed = currentCount <= limit;
        long remaining = Math.max(0, limit - currentCount);

        return new RateLimitResponse(allowed, remaining, 0);
    }
}
