package Multithreading;
import java.util.HashMap;
import java.util.Map;

class LRUCache {

    class Node {
        int key, value;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();

        head = new Node(-1, -1);
        tail = new Node(-1, -1);

        head.next = tail;
        tail.prev = head;
    }

    // ---------- Internal helpers (caller already synchronized)
    private void addNode(Node node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToFront(Node node) {
        removeNode(node);
        addNode(node);
    }
    // --------------------------------------------------------

    public synchronized int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }

        moveToFront(node);
        return node.value;
    }

    public synchronized void put(int key, int value) {
        Node node = map.get(key);

        if (node != null) {
            node.value = value;
            moveToFront(node);
            return;
        }

        if (map.size() == capacity) {
            Node lru = tail.prev;
            map.remove(lru.key);
            removeNode(lru);
        }

        Node newNode = new Node(key, value);
        addNode(newNode);
        map.put(key, newNode);
    }
}

public class LruCacheExample {
    public static void main(String[] args) throws InterruptedException {

        LRUCache cache = new LRUCache(2);

        Runnable writer1 = () -> {
            cache.put(1, 100);
            cache.put(2, 200);
        };

        Runnable writer2 = () -> {
            cache.put(3, 300); // should evict key 1 or 2 depending on timing
        };

        Runnable reader = () -> {
            System.out.println("get(1) = " + cache.get(1));
            System.out.println("get(2) = " + cache.get(2));
            System.out.println("get(3) = " + cache.get(3));
        };

        Thread t1 = new Thread(writer1);
        Thread t2 = new Thread(writer2);
        Thread t3 = new Thread(reader);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        // Final deterministic check
        System.out.println("\nFinal State Check:");
        System.out.println("get(1) = " + cache.get(1));
        System.out.println("get(2) = " + cache.get(2));
        System.out.println("get(3) = " + cache.get(3));
    }
}
