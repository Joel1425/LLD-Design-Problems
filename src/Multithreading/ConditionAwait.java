package Multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PrintEvenOdd{
    private int n;
    private int current = 1;
    Lock lock;
    Condition odd, even;
    public PrintEvenOdd(int n) {
        this.n = n;
        this.lock = new ReentrantLock();
        this.odd = this.lock.newCondition();
        this.even = this.lock.newCondition();
    }

    public void printEven(){
        while(true){
            this.lock.lock();
            try{
                while(this.current <= this.n && this.current%2==1){
                    this.even.await();
                }
                if (this.current > this.n){
                    signalAll();
                    return;
                }
                System.out.print(this.current + " ");
                this.current++;
                signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                this.lock.unlock();
            }
        }
    }

    public void printOdd(){
        while(true){
            this.lock.lock();
            try{
                while(this.current <= this.n && this.current%2==0){
                    this.odd.await();
                }
                if (this.current > this.n){
                    signalAll();
                    return;
                }
                System.out.print(this.current + " ");
                this.current++;
                signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                this.lock.unlock();
            }
        }
    }

    private void signalAll(){
        this.odd.signal();
        this.even.signal();
    }
}
public class ConditionAwait {
    public static void main(String[] args) {
        PrintEvenOdd printEvenOdd = new PrintEvenOdd(10);
        new Thread(()->{
            for (int i=0;i<10;i++){
                printEvenOdd.printOdd();
            }
        }, "OddThread").start();
        new Thread(()->{
            for (int i=0;i<10;i++){
                printEvenOdd.printEven();
            }
        }, "EvenThread").start();
    }
}
