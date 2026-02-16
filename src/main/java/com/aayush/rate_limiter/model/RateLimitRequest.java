package com.aayush.rate_limiter.model;

public class RateLimitRequest {

    private String clientId;
    private String endpoint;

    public RateLimitRequest() {}

    public RateLimitRequest(String clientId, String endpoint) {
        this.clientId = clientId;
        this.endpoint = endpoint;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
