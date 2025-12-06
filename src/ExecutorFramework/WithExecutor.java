package ExecutorFramework;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WithExecutor {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        long start = System.currentTimeMillis();
        for (int i=1;i<10;i++){
            int factI = i;
            executor.submit(() -> {
                System.out.println("Factorial of " + factI + " = " + factorial(factI));
            });
        }
        executor.shutdown(); // Mandatory to SHUTDOWN the executor: Good Practice
        try {
            executor.awaitTermination(100, TimeUnit.SECONDS); // For join(), if not completed in 100s
            // it will throw an exception
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
