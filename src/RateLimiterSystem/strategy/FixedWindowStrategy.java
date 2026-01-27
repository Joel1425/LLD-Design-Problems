package RateLimiterSystem.strategy;

import java.util.ArrayList;
import java.util.Map;

public class FixedWindowStrategy implements RateLimitingStrategy {

    @Override
    public boolean allowRequest(
            ArrayList<Long> timestamps,
            long currentTime,
            int limit,
            long windowSize) {

        // Remove old timestamps
        timestamps.removeIf(ts -> currentTime - ts >= windowSize);

        // Check limit
        if (timestamps.size() >= limit) {
            return false;
        }

        // Record allowed request
        timestamps.add(currentTime);
        return true;
    }
}
