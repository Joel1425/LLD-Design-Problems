package CouponDiscounts;

public class Coupon {
    String code;

    public Coupon(String code) {
        this.code = code;
    }

    public String getProductVariant(){
        // Use regex to split the string into variant and discount
        return this.code.replaceAll("\\d", ""); // Remove digits
    }

    public double getDiscount(){
        return Double.parseDouble(this.code.replaceAll("\\D", "")); // Remove non-digits;
    }
}
