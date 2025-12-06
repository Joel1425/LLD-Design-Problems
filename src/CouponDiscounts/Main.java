package CouponDiscounts;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        Product sofa = new Sofa("SOFA001", "MapleWoodSofa", 21000, ProductVariant.FURNITURE);
        List<Coupon> sofaCoupons = new ArrayList<>();
        sofaCoupons.add(new Coupon("PERCENTAGE30"));
        sofa.setCoupons(sofaCoupons);
        cart.addProduct(sofa);
        Product fan = new Fan("FAN001", "HavesFan", 700, ProductVariant.ELECTRONICS);
        List<Coupon> fanCoupons = new ArrayList<>();
        fanCoupons.add(new Coupon("ELECTRONICS15"));
        fanCoupons.add(new Coupon("PERCENTAGE10"));
        fan.setCoupons(fanCoupons);
        cart.addProduct(fan);
        cart.generateBill();
    }
}
