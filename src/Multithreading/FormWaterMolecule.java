package Multithreading;
class H2O {
    private int hCount = 0;
    private int oCount = 0;

    public H2O() {}

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        synchronized (this) {
            while (hCount == 2) { // already have 2 hydrogens, wait for oxygen to reset
                wait();
            }
            releaseHydrogen.run();
            hCount++;
            if (hCount == 2 && oCount == 1) {
                // molecule complete, reset and notify all
                hCount = 0;
                oCount = 0;
            }
            notifyAll();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        synchronized (this) {
            while (oCount == 1) { // already have an oxygen, wait for reset
                wait();
            }
            while (hCount < 2) { // wait until 2 hydrogens are available
                wait();
            }
            releaseOxygen.run();
            oCount++;
            if (hCount == 2 && oCount == 1) {
                // molecule complete, reset and notify all
                hCount = 0;
                oCount = 0;
            }
            notifyAll();
        }
    }
}

public class FormWaterMolecule{
    public static void main(String[] args) {
        H2O h2o = new H2O();
        new Thread(() -> {
            for (int i=0;i<10;i++){
                try {
                    h2o.hydrogen(() -> System.out.print("H"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "HydrogenThread").start();
        new Thread(() -> {
            for (int i=0;i<5;i++){
                try {
                    h2o.oxygen(() -> System.out.print("O"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "OxygenThread").start();
    }
}
