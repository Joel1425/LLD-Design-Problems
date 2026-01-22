package Multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

interface Connection {
    void open();
    void close();
}

class DBConnection implements Connection {
    private final int id;

    public DBConnection(int id) {
        this.id = id;
    }

    @Override
    public void open() {
        System.out.println("Opening connection " + id);
    }

    @Override
    public void close() {
        System.out.println("Closing connection " + id);
    }
}

class BlockingQueueUtil{
    private final BlockingQueue<Connection> pool;

    public BlockingQueueUtil(int maxSize) {
        this.pool = new LinkedBlockingQueue<>(maxSize);
        init(maxSize);
    }

    public void init(int maxSize){
        for(int i=0;i<maxSize;i++){
            Connection conn = new DBConnection(i);
            conn.open();
            pool.offer(conn);
        }
    }

    public Connection getConnection() throws InterruptedException {
        return pool.take();    // blocks if empty
    }

    public void releaseConnection(Connection conn) throws InterruptedException {
        if (conn != null) {
            pool.put(conn);   // blocks if full
        }
    }

    public void shutdown() {
        for (Connection conn : pool) {
            conn.close();
        }
    }
}

public class BlockingQueueProblem {
    public static void main(String[] args) {
        int POOL_SIZE = 2;
        BlockingQueueUtil system = new BlockingQueueUtil(POOL_SIZE);
        for (int i=1;i<=3;i++){
            String threadName = "Thread-"+i;
            new Thread(() -> {
                Connection conn = null;
                try {
                    conn = system.getConnection();
                    System.out.println("[ACQUIRED] "+threadName);
                    Thread.sleep(2000);
                    system.releaseConnection(conn);
                    System.out.println("[RELEASED] "+threadName);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    if (conn != null ){
                        try {
                            system.releaseConnection(conn);
                            System.out.println("[RELEASED] " + threadName);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }, threadName).start();
        }
    }
}
