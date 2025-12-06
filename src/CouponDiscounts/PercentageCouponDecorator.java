package CouponDiscounts;

public class PercentageCouponDecorator extends CouponDecorator{
    Product product;
    double discount;

    public PercentageCouponDecorator(Product product, double discount) {
        this.product = product;
        this.discount = discount;
    }

    @Override
    public double getPrice() {
        double price = this.product.getPrice();
        return price - (price * this.discount)/100.0;
    }
}
