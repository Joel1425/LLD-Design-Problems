package CouponDiscounts;

public class Sofa extends Product{
    public Sofa(String id, String name, double price, ProductVariant productVariant) {
        super(id, name, price, productVariant);
    }
    @Override
    public double getPrice() {
        return this.price;
    }

}
