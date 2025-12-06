package CouponDiscounts;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<Product> products;

    public ShoppingCart(){
        products = new ArrayList<>();
    }

    public void addProduct(Product product){
        List<Coupon> coupons = product.getCoupons();
        for (Coupon coupon: coupons){
            String productVariant = coupon.getProductVariant();
            double discount = coupon.getDiscount();
            switch (productVariant){
                case "ELECTRONICS":
                    product = new TypeCouponDecorator(product, discount, ProductVariant.ELECTRONICS);
                    break;
                case "PERCENTAGE":
                    product = new PercentageCouponDecorator(product, discount);
                    break;
            }
        }
        this.products.add(product);
    }

    public double getTotalPrice(){
        double total = 0.0;
        for (Product product: products){
            total += product.getPrice();
        }
        return total;
    }

    public void generateBill(){
        System.out.println("Total Price: "+this.getTotalPrice());
    }
}
