package ExecutorFramework;

import java.sql.Time;

public class WithoutExecutor {
    public static void main(String[] args) {
        Thread []thread = new Thread[9];
        long start = System.currentTimeMillis();
        for (int i=1;i<10;i++){
            int factI = i;
            thread[i-1] = new Thread(()->{
                System.out.println("Factorial of " + factI + " = " + factorial(factI));
            }, "Thread"+i);
            thread[i-1].start();
        }
        for (int i=1;i<10;i++){
            try {
                thread[i-1].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Total Time Taken = " + (System.currentTimeMillis()-start));
    }
    public static int factorial( int num ){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        int res = 1;
        for (int i=1;i<=num;i++){
            res *= i;
        }
        return res;
    }
}
