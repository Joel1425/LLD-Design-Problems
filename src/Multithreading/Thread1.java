package Multithreading;

public class Thread1 extends Thread{ // This works, BUT...
    // What if this class wants to extend any other class? JAVA does NOT follow Multiple Inheritance
    @Override
    public void run(){
        for (int i=1;i<=10;i++){
            System.out.println("Inside Thread1 "+i);
        }
    }
}
