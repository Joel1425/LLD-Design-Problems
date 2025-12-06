import java.util.ArrayList;
import java.util.Arrays;

interface PrintingStrategy{
    public void print(ArrayList<Integer> numbers);
}
class ReverseStrategy implements PrintingStrategy{
    @Override
    public void print(ArrayList<Integer> numbers){
        for (int i=numbers.size()-1;i>=0;i--){
            System.out.print(numbers.get(i)+" ");
        }
        System.out.println();
    }
}
class ForwardStrategy implements PrintingStrategy{
    @Override
    public void print(ArrayList<Integer> numbers){
        for (Integer number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}
class PrintingContext{
    private PrintingStrategy printingStrategy;
    public void setPrintingStrategy( PrintingStrategy printingStrategy){
        this.printingStrategy = printingStrategy;
    }
    public void printNumbers(ArrayList<Integer> numbers){
        if(this.printingStrategy == null){
            System.out.println("No Printing Strategy");
        }else{
            this.printingStrategy.print(numbers);
        }
    }
}
public class StrategyDesignPatternExample {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));
        PrintingContext printingContext = new PrintingContext();
        printingContext.setPrintingStrategy(new ReverseStrategy());
        printingContext.printNumbers(numbers);
        printingContext.setPrintingStrategy(new ForwardStrategy());
        printingContext.printNumbers(numbers);
    }
}
