package Multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CallCenter {
    private final ExecutorService executorService;

    public CallCenter(int agentCount) {
        // Fixed pool of agents (simulates available call handlers)
        this.executorService = Executors.newFixedThreadPool(agentCount);
    }

    // Method to handle incoming customer calls
    public void receiveCall(String customerName) {
        // Submitting each call as a separate task
        executorService.submit(() -> handleCall(customerName));
    }

    // Actual handling of a call (simulating processing time)
    private void handleCall(String customerName) {
        System.out.println(Thread.currentThread().getName() + " attending " + customerName);
        try {
            // Simulate call duration between 2–5 seconds
            Thread.sleep(2000 + (int) (Math.random() * 3000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + " finished with " + customerName);
    }

    // Gracefully shutdown the call center
    public void closeCenter() {
        executorService.shutdown();
    }
}

public class CallCenterLLD {
    public static void main(String[] args) {
        CallCenter callCenter = new CallCenter(3);  // 3 agents available

        // Simulate 10 customers calling in
        for (int i = 1; i <= 10; i++) {
            String customerName = "Customer-" + i;
            System.out.println(customerName + " is waiting for an available agent...");
            callCenter.receiveCall(customerName);
            try {
                // Small gap between calls to simulate real arrivals
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Close call center after all customers are handled
        callCenter.closeCenter();
        System.out.println("Call center closed for the day!");
    }
}