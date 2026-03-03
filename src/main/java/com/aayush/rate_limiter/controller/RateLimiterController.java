package com.aayush.rate_limiter.controller;

import com.aayush.rate_limiter.model.RateLimitRequest;
import com.aayush.rate_limiter.model.RateLimitResponse;
import com.aayush.rate_limiter.service.RateLimiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/rate-limit")
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/check")
    public ResponseEntity<RateLimitResponse> check(@RequestBody RateLimitRequest request) {

        RateLimitResponse response =
                rateLimiterService.check(request.getClientId(), request.getEndpoint());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RateLimit-Remaining", String.valueOf(response.getRemaining()));
        headers.add("Retry-After", String.valueOf(response.getRetryAfterMs()));

        if (!response.isAllowed()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .headers(headers)
                    .body(response);
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    }
}
