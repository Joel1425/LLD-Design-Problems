package Multithreading;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/*
    Problem statement:
    1. One barber, one barber chair, and N waiting chairs.
    2. If no customer, the barber sleeps in his chair.
    3. A customer who arrives:
        Wakes the barber if he is sleeping.
        Waits if there are free chairs.
        Leaves if no chairs are available.

    The challenge is to safely synchronize customers and the barber so that:
        Only one customer is in the barber’s chair at a time.
        Customers either wait or leave when seats are full.
        The barber works when customers are available and sleeps otherwise.
*/
class SleepingBarber {
    private final Queue<String> waitingQueue;
    private final int n;
    private boolean shopOpen = true;

    public SleepingBarber(int n) {
        this.n = n;
        this.waitingQueue = new LinkedList<>();
    }

    public synchronized void waitOrLeave(String person) {
        if (waitingQueue.size() == n) {
            System.out.println("WAITING QUEUE FULL: " + person + " is leaving!");
        } else {
            waitingQueue.offer(person);
            System.out.println(person + " is waiting.");
            notify(); // Wake up barber if sleeping
        }
    }

    public synchronized void closeShop() {
        shopOpen = false;
        notify();
    }

    public void cutHair() {
        while (true) {
            String currentCustomer;
            synchronized (this) {
                while (waitingQueue.isEmpty() && shopOpen) {
                    try {
                        System.out.println("Barber is sleeping...");
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                if (!shopOpen) {
                    break; // No more customers coming
                }
                // Remove customer while holding lock
                currentCustomer = waitingQueue.poll();
            }

            // Now process haircut outside synchronized block
            System.out.println("Barber is CUTTING HAIR: " + currentCustomer);
            try {
                Thread.sleep(1500); // Simulate time to cut hair
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("No more customers. Barber is going home.");
    }
}

public class SleepingBarberProblem {
    public static void main(String[] args) {
        SleepingBarber sb = new SleepingBarber(3);

        // Barber thread
        Thread barberThread = new Thread(sb::cutHair, "Barber");
        barberThread.start();

        // Customer thread
        new Thread(() -> {
            for (int i = 1; i <= 30; i++) {
                sb.waitOrLeave("Person" + i);
                try {
                    Thread.sleep(100); // customers coming faster
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            sb.closeShop();
        }, "Customers").start();
    }
}