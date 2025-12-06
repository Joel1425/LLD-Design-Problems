package Multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount{
    private int balance;
    private Lock lock;
    public BankAccount(int balance) {
        this.balance = balance;
        this.lock = new ReentrantLock();
    }

    public void WithdrawAmount( int amount ){
        System.out.println(Thread.currentThread().getName() + " requesting to withdraw " + amount);
        try{
            if (this.lock.tryLock(1000, TimeUnit.MICROSECONDS)) {
                try {
                    if (amount <= balance) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.println(Thread.currentThread().getName() + " Withdrawn " + amount);
                        this.balance -= amount;
                        System.out.println(Thread.currentThread().getName() + " Remaining Balance " + this.balance);
                    } else {
                        System.out.println(Thread.currentThread().getName() + " Insufficient Balance ");
                    }
                } finally {
                    this.lock.unlock();  // 🔓 always unlock if lock was acquired
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " Did NOT get the LOCK ");
            }
        } catch (Exception e){
            Thread.currentThread().interrupt();
        }
    }
}
public class ExplicitLocking {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(10);
        new Thread(()->{
            bankAccount.WithdrawAmount(50);
        },"Thread1").start();
        new Thread(()->{
            bankAccount.WithdrawAmount(10);
        },"Thread2").start();
    }
}
