package Multithreading;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting MAIN");

        Thread t1 = new Thread1(); // Has issues when we want to extend other class. So AVOID
        t1.start();

        Thread t2 = new Thread(new Thread2()); // USE, as we can implement mutiple interfaces.
        t2.start();

        System.out.println("Exiting MAIN");
    }
}
