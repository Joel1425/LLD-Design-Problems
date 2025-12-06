# Problem Statement
**Given Shopping cart with products and coupons and calculate the net price after applying coupons on products.
Coupons can be of different types with certain conditions.**

**1. N% off that is 10% off for all the products**

**2. P% off on next item**

**3.D% off on all items of Type T.**

Sequentially wants to apply all the coupons on the cart and get the Total amount.

## UML Diagram

***Product*** <br /> 
name: String <br />
id: String <br />
type: String <br />
price: double <br />
coupons: List< Coupon   > <br />

