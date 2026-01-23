package ATMSystem.model;

public class WithdrawRequest {
    public String cardNumber;
    public int pin;
    public int amount;
    public int remainingAmount;
    public User user;   // set after card validation

    public WithdrawRequest(String cardNumber, int pin, int amount) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.amount = amount;
        this.remainingAmount = amount;
    }
}

