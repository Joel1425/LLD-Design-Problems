package ATMSystem.model;

public class User {
    private String cardNumber;
    private int pin;
    private int balance;

    public User(String cardNumber, int pin, int balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public void deductBalance(int amount) {
        this.balance -= amount;
    }
}
