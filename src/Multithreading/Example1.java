package Multithreading;

class PrintChar{
    private Object lock;
    int turn;
    public PrintChar() {
        this.lock = new Object();
        this.turn = 0;
    }

    public void printA() {
        synchronized (this.lock) {
            while (turn != 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.print("A");
            this.turn = (this.turn + 1) % 3;
            lock.notifyAll();
        }
    }
    public void printB() {
        synchronized (this.lock) {
            while (turn != 1) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.print("B");
            this.turn = (this.turn + 1) % 3;
            lock.notifyAll();
        }
    }
    public void printC(){
        synchronized (this.lock){
            while(turn != 2){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.print("C");
            this.turn = (this.turn+1)%3;
            lock.notifyAll();
        }
    }
}
public class Example1 {
    public static void main(String[] args) {
        PrintChar printChar = new PrintChar();
        new Thread(()->{
            for(int i=0;i<5;i++){
                printChar.printA();
            }
        }, "thread1").start();
        new Thread(()->{
            for(int i=0;i<5;i++){
                printChar.printB();
            }
        }, "thread2").start();
        new Thread(()->{
            for(int i=0;i<5;i++){
                printChar.printC();
            }
        }, "thread3").start();
    }
}
