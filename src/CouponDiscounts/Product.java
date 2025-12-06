package CouponDiscounts;

import java.util.ArrayList;
import java.util.List;

public abstract class Product {
    String id;
    String name;
    double price;
    ProductVariant productVariant;
    List<Coupon> coupons;

    public Product(){}
    public Product(String id, String name, double price, ProductVariant productVariant ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productVariant = productVariant;
        this.coupons = new ArrayList<>();
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public List<Coupon> getCoupons() {
        return this.coupons;
    }

    public double getOriginalPrice(){
        return this.price;
    }

    public abstract double getPrice();

    public ProductVariant getProductVariant(){
        return this.productVariant;
    }
}
