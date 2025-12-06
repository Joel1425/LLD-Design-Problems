package VendingMachine;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    String id;
    List<Product> productList = new ArrayList<>();

    public Shelf(String id, List<Product> productList) {
        this.id = id;
        this.productList = productList;
    }
}
