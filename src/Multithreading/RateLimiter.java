package Multithreading;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

class RateLimiterUtil{
    private final int maxRequests;
    private final long windowSize;
    private final HashMap<String, Deque<Long>> requests;

    public RateLimiterUtil(int maxRequests, long windowSize) {
        this.maxRequests = maxRequests;
        this.windowSize = windowSize;
        requests = new HashMap<>();
    }

    public boolean isAllowed( String client ){
        long now = System.currentTimeMillis();
        Deque<Long> timestamps;
        synchronized (requests) {
            timestamps = requests.get(client);
            if (timestamps == null) {
                timestamps = new LinkedList<>();
                requests.put(client, timestamps);
            }
        }
        synchronized (timestamps){
            while (!timestamps.isEmpty() && now-timestamps.peekFirst() >= windowSize){
                timestamps.pollFirst();
            }
            if (timestamps.size() >= this.maxRequests){
                return false;
            }
            timestamps.addLast(now);
            return true;
        }
    }
}
public class RateLimiter {
    public static void main(String[] args) {
        RateLimiterUtil rl = new RateLimiterUtil(5, 1000);
        new Thread(() -> {
            for (int i=1;i<20;i++){
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                if (rl.isAllowed("Client-1")){
                    System.out.println("Client-1 Request ALLOWED at "+System.currentTimeMillis());
                }else{
                    System.out.println("Client-1 Request DROPPED at "+System.currentTimeMillis());
                }
            }
        }, "Client-1Thread").start();
        new Thread(() -> {
            for (int i=1;i<20;i++){
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                if (rl.isAllowed("Client-2")){
                    System.out.println("Client-2 Request ALLOWED at "+System.currentTimeMillis());
                }else{
                    System.out.println("Client-2 Request DROPPED at "+System.currentTimeMillis());
                }
            }
        }, "Client-2Thread").start();
    }
}
