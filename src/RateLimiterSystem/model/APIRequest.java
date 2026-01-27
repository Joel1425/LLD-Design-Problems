package RateLimiterSystem.model;

public class APIRequest {
    private String apiName;
    private String clientIP;
    private Long timestamp;

    public APIRequest(String apiName, String clientIP, Long timestamp) {
        this.apiName = apiName;
        this.clientIP = clientIP;
        this.timestamp = timestamp;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
