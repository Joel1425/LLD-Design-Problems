package SplitwisePackage;

public class Split {
    User user;
    float amount;

    public Split(User user, float amount) {
        this.user = user;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public float getAmount() {
        return amount;
    }
}
