package RateLimiterSystem;

import RateLimiterSystem.model.APIRequest;
import RateLimiterSystem.model.RateLimiterConfig;
import RateLimiterSystem.rateLimiter.RateLimiter;
import RateLimiterSystem.strategy.FixedWindowStrategy;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // 1️⃣ Configure rate limiter
        RateLimiter rateLimiter = RateLimiter.getInstance();

        // Allow 3 requests per 5 seconds for /login API
        RateLimiterConfig loginConfig =
                new RateLimiterConfig(
                        3,
                        5000, // 5 seconds
                        new FixedWindowStrategy()
                );

        RateLimiter.addAPIConfig("/login", loginConfig);

        String clientIP = "192.168.1.10";

        // 2️⃣ Fire requests
        System.out.println("---- Sending requests ----");

        for (int i = 1; i <= 5; i++) {
            APIRequest request =
                    new APIRequest(
                            "/login",
                            clientIP,
                            System.currentTimeMillis()
                    );

            boolean allowed = rateLimiter.allowRequest(request);
            System.out.println(
                    "Request " + i + " allowed? " + allowed
            );

            Thread.sleep(500); // 0.5 sec gap
        }

        // 3️⃣ Wait for window to reset
        System.out.println("\n---- Waiting for window reset ----");
        Thread.sleep(5000);

        // 4️⃣ Try again
        System.out.println("\n---- Sending requests after window ----");

        for (int i = 1; i <= 4; i++) {
            APIRequest request =
                    new APIRequest(
                            "/login",
                            clientIP,
                            System.currentTimeMillis()
                    );

            boolean allowed = rateLimiter.allowRequest(request);
            System.out.println(
                    "Request " + i + " allowed? " + allowed
            );
            Thread.sleep(500); // 0.5 sec gap
        }
    }
}

