package com.aayush.rate_limiter.model;

public class RateLimitResponse {

    private boolean allowed;
    private long remaining;
    private long retryAfterMs;

    public RateLimitResponse() {}

    public RateLimitResponse(boolean allowed, long remaining, long retryAfterMs) {
        this.allowed = allowed;
        this.remaining = remaining;
        this.retryAfterMs = retryAfterMs;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public long getRemaining() {
        return remaining;
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    public long getRetryAfterMs() {
        return retryAfterMs;
    }

    public void setRetryAfterMs(long retryAfterMs) {
        this.retryAfterMs = retryAfterMs;
    }
}
