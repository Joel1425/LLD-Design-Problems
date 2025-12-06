package Multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

class FizzBuzz{
    private final int n;
    private int current;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition fizzCondition = lock.newCondition();
    private final Condition buzzCondition = lock.newCondition();
    private final Condition fizzbuzzCondition = lock.newCondition();
    private final Condition numberCondition = lock.newCondition();

    public FizzBuzz(int n) {
        this.n = n;
        this.current = 1;
    }

    public void fizz(Runnable printFizz) throws InterruptedException{
        while(true){
            lock.lock();
            try {
                while ( current<=n && ( current%3!=0 || current%5==0 )){
                    fizzCondition.await();
                }
                if (current>n) { return; }
                printFizz.run();
                current++;
                signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
    public void buzz(Runnable printBuzz) throws InterruptedException{
        while(true){
            lock.lock();
            try {
                while ( current<=n && ( current%3==0 || current%5!=0 )){
                    buzzCondition.await();
                }
                if (current>n) { return; }
                printBuzz.run();
                current++;
                signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException{
        while(true){
            lock.lock();
            try {
                while ( current<=n && ( current%3!=0 || current%5!=0 )){
                    fizzbuzzCondition.await();
                }
                if (current>n) { return; }
                printFizzBuzz.run();
                current++;
                signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
    public void number(IntConsumer printNumber) throws InterruptedException{
        while(true){
            lock.lock();
            try {
                while ( current<=n && ( current%3==0 || current%5==0 )){
                    numberCondition.await();
                }
                if (current>n) { return; }
                printNumber.accept(current);
                current++;
                signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    private void signalAll() {
        fizzCondition.signal();
        buzzCondition.signal();
        fizzbuzzCondition.signal();
        numberCondition.signal();
    }
}
public class FizzBuzzProblem {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(20);
        new Thread(() -> {
            try{
                fizzBuzz.fizz(() -> System.out.println("fizz"));
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }, "fizzThread").start();
        new Thread(() -> {
            try{
                fizzBuzz.buzz(() -> System.out.println("buzz"));
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }, "buzzThread").start();
        new Thread(() -> {
            try{
                fizzBuzz.fizzbuzz(() -> System.out.println("fizzbuzz"));
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }, "fizzbuzzThread").start();
        new Thread(() -> {
            try{
                fizzBuzz.number(x -> System.out.println(x));
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }, "fizzbuzzThread").start();
    }
}
