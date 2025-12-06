package Multithreading;

class PrintingUtil {
    private int currentOdd, currentEven;
    boolean isEvenTurn;
    private final Object lock;
    public PrintingUtil(int currentOdd, int currentEven) {
        this.currentOdd = currentOdd;
        this.currentEven = currentEven;
        this.lock = new Object();
        this.isEvenTurn = true;
    }

    public void printOddNumber(){
        synchronized (this.lock){
            while (isEvenTurn){
                try{
                    this.lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }
            System.out.println("ODD "+this.currentOdd);
            this.currentOdd+=2;
            this.isEvenTurn = true;
            this.lock.notifyAll();
        }
    }

    public void printEvenNumber(){
        synchronized (this.lock){
            while (!isEvenTurn){
                try{
                    this.lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }
            System.out.println("EVEN "+this.currentEven);
            this.currentEven+=2;
            this.isEvenTurn = false;
            this.lock.notifyAll();
        }
    }

}
public class PrintNumbers {
    public static void main(String[] args) {
        PrintingUtil printingUtil = new PrintingUtil(1,0);
        new Thread(()->{
            for (int i=0;i<10;i++){
                printingUtil.printEvenNumber();
            }
        }, "EVEN PRINTING THREAD").start();
        new Thread(()->{
            for (int i=0;i<10;i++){
                printingUtil.printOddNumber();
            }
        }, "ODD PRINTING THREAD").start();
    }
}
