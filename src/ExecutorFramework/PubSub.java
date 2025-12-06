package ExecutorFramework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class MessageBroker{
    // Topic: Messages
    private final ConcurrentHashMap<String, List<String>> topics;
    // Subscriber Offsets, Topic : { Subscriber:Offset }
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> offsets;

    public MessageBroker() {
        this.topics = new ConcurrentHashMap<>();
        this.offsets = new ConcurrentHashMap<>();
    }

    // Publish a message to a topic
    public void publish(String topic, String message){
        this.topics.putIfAbsent(topic, new ArrayList<>());
        List<String> messages = this.topics.get(topic);
        synchronized (messages){
            messages.add(message);
            System.out.println( "[PUBLISHER] Published " +
                                message + " to topic " + topic +
                                " @ " + System.currentTimeMillis() );
        }
    }

    // Subscribe a message
    public void subscribe(String subscriber, String topic, boolean fromBeginning){
        this.topics.putIfAbsent(topic, new ArrayList<>());
        this.offsets.putIfAbsent(topic, new ConcurrentHashMap<>());

        List<String> messages = this.topics.get(topic);
        ConcurrentHashMap<String, Integer> subscribersOffset = this.offsets.get(topic);

        synchronized (messages){
            subscribersOffset.putIfAbsent(subscriber, fromBeginning?0:messages.size());
        }

        new Thread(() -> {
            while(true){
                synchronized (messages){
                    int currentIndex = subscribersOffset.get(subscriber);
                    while(currentIndex < messages.size()){
                        System.out.println( "[SUBSCRIBER] " + subscriber +  " Consumed " +
                                            messages.get(currentIndex) + " from topic " + topic +
                                            " @ " + System.currentTimeMillis() );
                        currentIndex++;
                        subscribersOffset.put(subscriber, currentIndex);
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, subscriber).start();
    }
}

public class PubSub {
    public static void main(String[] args) {
        MessageBroker messageBroker = new MessageBroker();
        messageBroker.subscribe("Subscriber-1", "SPORTS", true);
        messageBroker.subscribe("Subscriber-2", "NEWS", true);
        messageBroker.subscribe("Subscriber-3", "SPORTS", false);
        messageBroker.subscribe("Subscriber-4", "NEWS", false);

        // Publishers
        new Thread(()->{
            for (int i=0;i<10;i++){
                messageBroker.publish("SPORTS", "SportsMessage"+i);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "SPORTS PUBLISHER THREAD").start();

        new Thread(()->{
            for (int i=0;i<10;i++){
                messageBroker.publish("NEWS", "NewsMessage"+i);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        }, "NEWS PUBLISHER THREAD").start();
    }
}
