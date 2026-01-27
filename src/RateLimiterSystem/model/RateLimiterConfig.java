package RateLimiterSystem.model;

import RateLimiterSystem.strategy.RateLimitingStrategy;

public class RateLimiterConfig {
    private int maxRequests;
    private long windowSizeInMillis;
    private RateLimitingStrategy rateLimitingStrategy;

    public RateLimiterConfig(int maxRequests, long windowSizeInMillis, RateLimitingStrategy rateLimitingStrategy) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.rateLimitingStrategy = rateLimitingStrategy;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    public long getWindowSizeInMillis() {
        return windowSizeInMillis;
    }

    public void setWindowSizeInMillis(long windowSizeInMillis) {
        this.windowSizeInMillis = windowSizeInMillis;
    }

    public RateLimitingStrategy getRateLimitingStrategy() {
        return rateLimitingStrategy;
    }

    public void setRateLimitingStrategy(RateLimitingStrategy rateLimitingStrategy) {
        this.rateLimitingStrategy = rateLimitingStrategy;
    }
}
