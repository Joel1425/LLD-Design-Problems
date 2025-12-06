package VendingMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VendingMachineUtil {
    private static VendingMachineUtil vendingMachine;
    List<Shelf> shelves;

    private VendingMachineUtil(){

    }

    public static VendingMachineUtil getInstance(){
        if (vendingMachine==null){
            vendingMachine = new VendingMachineUtil();
        }
        return vendingMachine;
    }

    // Init products
    public void initProducts(){
        Random rand = new Random();
        List<Shelf> shelvesData = new ArrayList<>();
        for (int i =0;i<4;i++){
            List<Product> productData = new ArrayList<>();
            for (int j=0;j<5;j++){
                int randomNum = rand.nextInt(10) + 1;
                String id = String.valueOf(i)+String.valueOf(j);
                Product product = new Product( id, "Product-"+id, randomNum*10.0, randomNum );
                productData.add(product);
            }
            Shelf shelf = new Shelf(String.valueOf(i), productData);
            shelvesData.add(shelf);
        }
        this.shelves = shelvesData;
    }

    // Print product state
    public void printState(){
        System.out.println("VENDING MACHINE");
        System.out.println();
        for (int i=0;i<this.shelves.size();i++){
            System.out.println("Shelf: " + i);
            for (int j=0;j<this.shelves.get(i).productList.size();j++){
                Product product = this.shelves.get(i).productList.get(j);
                System.out.print(product.getId() + " " + product.getName() + " " + product.getPrice() + " " + product.getUnits() + "    ");
            }
            System.out.println();
        }
    }

    public void hardCode( String pid ){
        // Optimise this
        for (int i=0;i<this.shelves.size();i++){
            for (int j=0;j<this.shelves.get(i).productList.size();j++){
                Product product = this.shelves.get(i).productList.get(j);
                if (product.getId().compareTo(pid) == 0) {
                    product.setUnits(0);
                }
            }
            System.out.println();
        }
    }

    public boolean isAvailable( String pid ){
        // Optimise this
        for (int i=0;i<this.shelves.size();i++){
            for (int j=0;j<this.shelves.get(i).productList.size();j++){
                Product product = this.shelves.get(i).productList.get(j);
                if (product.getId().compareTo(pid) == 0) {
                    if (product.getUnits() > 0){
                        System.out.println( "Product AVAILABLE ");
                        return true;
                    }
                }
            }
            System.out.println();
        }
        System.out.println( "Product UNAVAILABLE ");
        return false;
    }

    public void Collector( User user, String pid, double cash ){
        // This will collect cash
        // Optimise this
        for (int i=0;i<this.shelves.size();i++){
            for (int j=0;j<this.shelves.get(i).productList.size();j++){
                Product product = this.shelves.get(i).productList.get(j);
                if (product.getId().compareTo(pid) == 0) {
                    if ( Validator(product, cash ) ){
                        double change = cash - product.getPrice();
                        product.decrementUnit();
                        CashDispenser( change );
                        ProductDispenser( user, product );
                    } else {
                        CashDispenser( cash );
                    }
                }
            }
        }
    }

    public void CashDispenser( double cash ){
        // This will dispense cash
        System.out.println( "Returning Amount " + cash );
    }

    public boolean Validator( Product product, double cash ){
        // Also add the input string
        if (product.getPrice() <= cash){
            System.out.println("VALID Amount given");
            return true;
        }
        return false;
    }

    public void ProductDispenser( User user, Product product){
        // This will dispense products
        System.out.println( "PRODUCT " + product.getName() + " given to " + user.getName() );
    }
}
