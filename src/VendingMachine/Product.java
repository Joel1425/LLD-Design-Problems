package VendingMachine;

public class Product {
    String id;
    String name;
    Double price;
    int units;

    public Product(String id, String name, Double price, int units) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.units = units;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUnits() {
        return this.units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void decrementUnit(){
        int currentUnits = getUnits();
        setUnits(currentUnits-1);
    }
}
