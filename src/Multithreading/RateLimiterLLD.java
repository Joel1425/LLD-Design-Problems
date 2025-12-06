package Multithreading;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

class RateLimiterService {
    private final int windowSize;   // seconds
    private final int maxRequests;
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Deque<Long>>> apiRequests;

    public RateLimiterService(int windowSize, int maxRequests) {
        this.windowSize = windowSize;
        this.maxRequests = maxRequests;
        this.apiRequests = new ConcurrentHashMap<>();
    }

    public void allowRequest(String user, String api) {
        this.apiRequests.putIfAbsent(user, new ConcurrentHashMap<>());
        ConcurrentHashMap<String, Deque<Long>> requests = this.apiRequests.get(user);
        requests.putIfAbsent(api, new ArrayDeque<>());
        Deque<Long> timestamps = requests.get(api);

        synchronized (timestamps) {
            long now = System.currentTimeMillis();

            // Remove stale requests (older than windowSize seconds)
            while (!timestamps.isEmpty() && (now - timestamps.peekFirst()) / 1000 >= this.windowSize) {
                timestamps.pollFirst();
            }

            // Check limit
            if (timestamps.size() >= this.maxRequests) {
                System.out.println("[DENIED] " + user + " " + api + " at " + now);
            } else {
                System.out.println("[ACCEPTED] " + user + " " + api + " at " + now);
                timestamps.addLast(now);
            }
        }
    }
}

public class RateLimiterLLD {
    public static void main(String[] args) {
        // allow max 3 requests per user per API in a 1-second sliding window
        RateLimiterService rateLimiterService = new RateLimiterService(1, 3);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                rateLimiterService.allowRequest("User-1", "/get/users");
                try {
                    Thread.sleep(150); // slightly off alignment, causes mix of ACCEPTED/DENIED
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                rateLimiterService.allowRequest("User-2", "/get/stores");
                try {
                    Thread.sleep(120); // different pacing to interleave
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}



/*
    Requirements:
    1. For 1 User for 1 function, max 5 requests/second
    2. The request must be dropped if > 5RPS
*/