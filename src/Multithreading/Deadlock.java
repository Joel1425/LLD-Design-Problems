package Multithreading;

class DeadlockUtil {
    private Object lock1, lock2;

    public DeadlockUtil() {
        this.lock1 = new Object();
        this.lock2 = new Object();
    }

    public void func1(){
        synchronized (this.lock1){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (this.lock2){
                System.out.println("Both locks acquired");
            }
        }
    }

    public void func2(){
        synchronized (this.lock2){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (this.lock1){
                System.out.println("Both locks acquired");
            }
        }
    }
}

public class Deadlock {
    public static void main(String[] args) {
        DeadlockUtil deadlockUtil = new DeadlockUtil();
        new Thread(deadlockUtil::func1, "thread1").start();
        new Thread(deadlockUtil::func2, "thread2").start();
    }
}
