import java.util.HashMap;

// Requirements:
//1. Shorten a URL
//2. Retrieve long URL from Short URL
class UrlShortener{
    private static UrlShortener instance;
    private final HashMap<String, String> database;
    private int token;
    private final String BASE62;
    private UrlShortener() {
        this.database = new HashMap<>();
        this.token = 100000;
        this.BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    }
    public static synchronized UrlShortener getInstance(){
        if (instance == null){
            instance = new UrlShortener();
        }
        return instance;
    }

    public synchronized String encode( String url ){
        int id = this.token;
        this.token++;
        StringBuilder sb = new StringBuilder();
        while(id>0){
            int current = id%62;
            sb.append(this.BASE62.charAt(current));
            id/=62;
        }
        sb.reverse();
        return sb.toString();
    }

    public synchronized String getShortenedUrl( String url ){
        String shortUrl = encode( url );
        this.database.put( shortUrl, url );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return shortUrl;
    }

    public synchronized String getLongUrl(String url) {
        return this.database.getOrDefault(url, "404_NOT_FOUND");
    }

    public synchronized void printDB() {
        System.out.println("\n--- DATABASE CONTENTS ---");
        for (var entry : database.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
        System.out.println("--------------------------\n");
    }
}

public class UrlShortenerLLD {
    public static void main(String[] args) throws InterruptedException {
        UrlShortener urlShortener = UrlShortener.getInstance();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            int val = i;
            threads[i] = new Thread(() -> {
                String link = "www.google.com-" + val;
                String shortUrl = urlShortener.getShortenedUrl(link);
                System.out.println(Thread.currentThread().getName() +
                        " shortened: " + link + " -> " + shortUrl);
            }, "Thread-" + i);

            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        // Print the final DB
        urlShortener.printDB();

        // Print NON existing url
        System.out.println( "Bab -> " + urlShortener.getLongUrl("Bab") );
    }
}
