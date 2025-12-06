package VendingMachine;

public class VMSystem {
    public static void main(String[] args) {
        VendingMachineUtil vendingMachine = VendingMachineUtil.getInstance();
        vendingMachine.initProducts();
//        vendingMachine.hardCode( "12");
        vendingMachine.printState();
        User user1 = new User("U1", "User-1" );
        System.out.println();
        System.out.println( "Buying Product " + "12" );
        boolean isAvailable = vendingMachine.isAvailable( "12" );
        if ( isAvailable ){
            System.out.println("Please enter cash!");
            System.out.println("Entering " + 100.0);
            vendingMachine.Collector( user1, "12", 100.0 );
            vendingMachine.printState();
        }

    }
}


//Shelf: 1
//10 Product-10 80.0 8    11 Product-11 100.0 10    12 Product-12 30.0 3    13 Product-13 40.0 4    14 Product-14 20.0 2
