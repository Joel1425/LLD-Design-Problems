import java.util.HashMap;
import java.util.Map;

// ---------------- STATE INTERFACE ----------------
interface VendingMachineState {
    void insertCoin(VendingMachine machine, int amount);
    void selectProduct(VendingMachine machine, String productCode);
    void dispense(VendingMachine machine);
}

// ---------------- STATES ----------------
class NoCoinState implements VendingMachineState {

    public void insertCoin(VendingMachine machine, int amount) {
        machine.addBalance(amount);
        System.out.println("Inserted ₹" + amount);
        machine.setState(new HasCoinState());
    }

    public void selectProduct(VendingMachine machine, String productCode) {
        System.out.println("Insert coin first");
    }

    public void dispense(VendingMachine machine) {
        System.out.println("Insert coin first");
    }
}

class HasCoinState implements VendingMachineState {

    public void insertCoin(VendingMachine machine, int amount) {
        machine.addBalance(amount);
        System.out.println("Added ₹" + amount);
    }

    public void selectProduct(VendingMachine machine, String productCode) {
        Product product = machine.getProduct(productCode);

        if (product == null) {
            System.out.println("Invalid product");
            return;
        }

        if (product.getQuantity() <= 0) {
            System.out.println("Product out of stock");
            return;
        }

        if (machine.getBalance() < product.getPrice()) {
            System.out.println("Insufficient balance");
            return;
        }

        machine.setSelectedProduct(product);
        machine.setState(new DispenseState());
        machine.dispense();
    }

    public void dispense(VendingMachine machine) {
        System.out.println("Select product first");
    }
}

class DispenseState implements VendingMachineState {

    public void insertCoin(VendingMachine machine, int amount) {
        System.out.println("Please wait, dispensing in progress");
    }

    public void selectProduct(VendingMachine machine, String productCode) {
        System.out.println("Please wait, dispensing in progress");
    }

    public void dispense(VendingMachine machine) {
        Product product = machine.getSelectedProduct();

        product.decreaseQuantity();
        machine.deductBalance(product.getPrice());

        System.out.println("Dispensed: " + product.getName());
        System.out.println("Remaining balance: ₹" + machine.getBalance());

        machine.clearSelectedProduct();
        machine.setState(new NoCoinState());
    }
}

// ---------------- DOMAIN OBJECT ----------------
class Product {
    private final String name;
    private final int price;
    private int quantity;

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void decreaseQuantity() {
        quantity--;
    }
}

// ---------------- CONTEXT ----------------
class VendingMachine {
    private VendingMachineState currentState;
    private final Map<String, Product> inventory = new HashMap<>();

    private int balance = 0;
    private Product selectedProduct;

    public VendingMachine() {
        currentState = new NoCoinState();
        loadProducts();
    }

    private void loadProducts() {
        inventory.put("A1", new Product("Coke", 25, 5));
        inventory.put("B1", new Product("Chips", 15, 3));
        inventory.put("C1", new Product("Water", 10, 10));
    }

    // -------- API METHODS --------
    public void insertCoin(int amount) {
        currentState.insertCoin(this, amount);
    }

    public void selectProduct(String productCode) {
        currentState.selectProduct(this, productCode);
    }

    public void dispense() {
        currentState.dispense(this);
    }

    // -------- INTERNAL HELPERS --------
    public void setState(VendingMachineState state) {
        this.currentState = state;
    }

    public void addBalance(int amount) {
        balance += amount;
    }

    public void deductBalance(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public Product getProduct(String code) {
        return inventory.get(code);
    }

    public void setSelectedProduct(Product product) {
        this.selectedProduct = product;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void clearSelectedProduct() {
        selectedProduct = null;
        balance = 0;
    }
}

// ---------------- DEMO ----------------
public class VendingMachineExample {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();

        machine.selectProduct("A1");   // Insert coin first
        machine.insertCoin(20);        // ₹20
        machine.selectProduct("A1");   // Insufficient balance
        machine.insertCoin(10);        // ₹30 total
        machine.selectProduct("A1");   // Dispense Coke
    }
}
