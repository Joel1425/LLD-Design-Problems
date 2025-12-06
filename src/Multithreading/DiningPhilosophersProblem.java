package Multithreading;

class DiningPhilosophers {

    Object leftFork = new Object();
    Object rightFork = new Object();

    public DiningPhilosophers() {}

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        synchronized(this.leftFork)
        {
            synchronized(this.rightFork)
            {
                pickLeftFork.run();
                pickRightFork.run();
                eat.run();
                putRightFork.run();
                putLeftFork.run();
            }

        }
    }
}
public class DiningPhilosophersProblem {
    public static void main(String[] args) {
        DiningPhilosophers dp = new DiningPhilosophers();

        // Create 5 philosopher threads
        for (int i = 0; i < 5; i++) {
            final int id = i; // effectively final for lambda
            new Thread(() -> {
                try {
                    dp.wantsToEat(
                            id,
                            () -> System.out.println("Philosopher " + id + " picks left fork"),
                            () -> System.out.println("Philosopher " + id + " picks right fork"),
                            () -> {
                                System.out.println("Philosopher " + id + " is eating");
                                try { Thread.sleep(500); } catch (InterruptedException _) {}
                            },
                            () -> System.out.println("Philosopher " + id + " puts right fork"),
                            () -> System.out.println("Philosopher " + id + " puts left fork")
                    );
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}

