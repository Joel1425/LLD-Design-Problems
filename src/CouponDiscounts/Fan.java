package CouponDiscounts;

public class Fan extends Product{
    public Fan(String id, String name, double price, ProductVariant productVariant) {
        super(id, name, price, productVariant);
    }

    @Override
    public double getPrice() {
        return this.price;
    }

}
