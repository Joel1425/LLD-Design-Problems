package RateLimiterSystem.rateLimiter;

import RateLimiterSystem.model.APIRequest;
import RateLimiterSystem.model.RateLimiterConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

    private static final Map<String, Map<String, ArrayList<Long>>> requestDB = new HashMap<>();
    private static final Map<String, RateLimiterConfig> apiConfigs = new HashMap<>();
    private static final RateLimiter INSTANCE = new RateLimiter();

    private RateLimiter(){}

    public static RateLimiter getInstance(){
        return INSTANCE;
    }

    public boolean allowRequest(APIRequest request) {

        String clientIP = request.getClientIP();
        String apiName = request.getApiName();
        long currentTime = request.getTimestamp();

        // Get config
        RateLimiterConfig config = apiConfigs.get(apiName);
        if (config == null) {
            throw new RuntimeException("No rate limit config for API: " + apiName);
        }

        // Get client map
        Map<String, ArrayList<Long>> clientData =
                requestDB.computeIfAbsent(clientIP, k -> new HashMap<>());

        // Get API timestamps
        ArrayList<Long> timestamps =
                clientData.computeIfAbsent(apiName, k -> new ArrayList<>());

        // Delegate decision to strategy
        return config.getRateLimitingStrategy().allowRequest(
                timestamps,
                currentTime,
                config.getMaxRequests(),
                config.getWindowSizeInMillis()
        );
    }


    public static void addAPIConfig( String apiRequest, RateLimiterConfig rateLimiterConfig ){
        apiConfigs.put(apiRequest, rateLimiterConfig);
    }
}
