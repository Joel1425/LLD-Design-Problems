package RateLimiterSystem.strategy;

import java.util.ArrayList;
import java.util.Map;

public interface RateLimitingStrategy {
    boolean allowRequest(
            ArrayList<Long> timestamps,
            long currentTime,
            int limit,
            long windowSize
    );
}