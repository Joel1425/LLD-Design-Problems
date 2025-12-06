package Multithreading;

import java.util.*;
import java.util.concurrent.*;

class MessageBroker {
    // Stores messages per topic
    private final Map<String, List<String>> topics = new ConcurrentHashMap<>();
    // Stores subscriber offsets: topic -> (subscriber -> offset)
    private final Map<String, Map<String, Integer>> subscriberOffsets = new ConcurrentHashMap<>();

    // Publish a message
    public void publish(String topic, String message) {
        topics.putIfAbsent(topic, new ArrayList<>());
        List<String> messages = topics.get(topic);
        synchronized (messages) {
            messages.add(message);
        }
        System.out.println("[Publisher] Published to " + topic + ": " + message);
    }

    // Subscribe with independent offset and fixed duration
    public void subscribe(String topic, String subscriberName, boolean fromBeginning, long durationMs) {
        topics.putIfAbsent(topic, new ArrayList<>());
        subscriberOffsets.putIfAbsent(topic, new ConcurrentHashMap<>());

        List<String> messages = topics.get(topic);
        Map<String, Integer> offsets = subscriberOffsets.get(topic);

        synchronized (messages) {
            offsets.putIfAbsent(subscriberName, fromBeginning ? 0 : messages.size());
        }

        new Thread(() -> {
            long endTime = System.currentTimeMillis() + durationMs;
            while (System.currentTimeMillis() < endTime) {
                synchronized (messages) {
                    int index = offsets.get(subscriberName);
                    while (index < messages.size()) {
                        System.out.println("[Subscriber-" + subscriberName + "] " + topic + ": " + messages.get(index));
                        index++;
                        offsets.put(subscriberName, index);
                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("[Subscriber-" + subscriberName + "] Stopped after " + (durationMs/1000) + " seconds.");
        }).start();
    }

    // Reset subscriber offset (replay from start or any position)
    public void resetOffset(String topic, String subscriberName, int newOffset) {
        List<String> messages = topics.get(topic);
        if (messages == null) {
            System.out.println("[Broker] Topic " + topic + " does not exist!");
            return;
        }
        synchronized (messages) {
            int safeOffset = Math.max(0, Math.min(newOffset, messages.size()));
            subscriberOffsets.get(topic).put(subscriberName, safeOffset);
            System.out.println("[Broker] Reset offset for " + subscriberName + " on " + topic + " to " + safeOffset);
        }
    }
}

// Demo
public class MessageQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        MessageBroker broker = new MessageBroker();

        // Subscribers run for 1 minute (60_000 ms)
        broker.subscribe("sports", "Alice", true, 60_000); // replay from beginning
        broker.subscribe("sports", "Bob", false, 60_000);  // only new messages
        broker.subscribe("news", "Charlie", true, 60_000); // replay
        broker.subscribe("news", "Daisy", false, 60_000);  // only new

        // Publishers
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                broker.publish("sports", "Sports Update " + i);
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                broker.publish("news", "Breaking News " + i);
                try { Thread.sleep(700); } catch (InterruptedException e) {}
            }
        }).start();

        // Reset Bob's offset after 2 seconds (replay from start)
        Thread.sleep(2000);
        broker.resetOffset("sports", "Bob", 0);
    }
}


