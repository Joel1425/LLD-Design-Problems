package CouponDiscounts;

import java.util.HashSet;

public class TypeCouponDecorator extends CouponDecorator{
    Product product;
    double discount;
    ProductVariant productVariant;
    HashSet<ProductVariant> eligibleVariants;

    public TypeCouponDecorator(Product product, double discount, ProductVariant productVariant) {
        this.product = product;
        this.discount = discount;
        this.productVariant = productVariant;
        this.eligibleVariants = new HashSet<>();
        initEligibleVariants();
    }

    public void initEligibleVariants(){
        this.eligibleVariants.add(ProductVariant.ELECTRONICS);
    }

    @Override
    public double getPrice() {
        double price = this.product.getPrice();
        if (this.eligibleVariants.contains(this.product.getProductVariant())) {
            return price - (price * this.discount) / 100.0;
        }
        return price;
    }
}
