package Multithreading;

import java.util.Arrays;

class Stack {
    private int[] arr;
    private int capacity;
    private int top;
    private Object lock;

    public Stack(int capacity) {
        this.arr = new int[capacity];
        this.capacity = capacity;
        this.top = -1;
        Arrays.fill(this.arr, Integer.MIN_VALUE);
        this.lock = new Object();
    }

    public boolean isEmpty(){
        if (this.top == -1)
            System.out.println("STACK is EMPTY");
        return this.top == -1;
    }

    public boolean isFull(){
        if (this.top == this.capacity - 1)
            System.out.println("STACK is FULL");
        return this.top == this.capacity - 1;
    }

    public boolean push( int element ){
        synchronized (this.lock){
            while (isFull()){
                try {
                    this.lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                return false;
            }
            this.top++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.arr[this.top] = element;
            this.lock.notifyAll();
            System.out.println("Pushed "+element+" into the STACK");
            return true;
        }

    }

    public int pop(){
        synchronized(this.lock){
            while (isEmpty()) {
                try {
                    this.lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                return Integer.MIN_VALUE;
            }
            int topElement = this.arr[this.top];
            this.arr[this.top] = Integer.MIN_VALUE;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.top--;
            this.lock.notifyAll();
            System.out.println("Popped " + topElement + " from the STACK");
            return topElement;
        }

    }
}
public class MutithreadingStack {
    public static void main(String[] args) {
        Stack stack = new Stack(5);

        Thread thread1 = new Thread(() -> {
            for(int i=0;i<10;i++){
                stack.push(i);
            }
        }, "PushStack");
        Thread thread2 = new Thread(() -> {
            for(int i=0;i<10;i++){
                stack.pop();
            }
        }, "PopStack");

        thread1.start();
        thread2.start();
    }
}

