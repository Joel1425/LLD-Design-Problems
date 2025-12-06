package Multithreading;

public class Thread2 implements Runnable{ // We should be using this, as JAVA supports
    // Implementing multiple interfaces
    @Override
    public void run(){
        for (int i=10;i>=1;i--){
            System.out.println("Inside Thread2 "+i);
        }
    }
}
