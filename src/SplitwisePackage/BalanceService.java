package SplitwisePackage;

import java.util.HashMap;

public class BalanceService {

    private static final BalanceSheet balanceSheet = new BalanceSheet();

    public void updateBalances(Expense expense) {
        balanceSheet.update(expense);
    }

    public HashMap<User, Float> getBalances(User user) {
        return balanceSheet.getUserBalances(user);
    }

    public void printBalances(User user) {
        System.out.println("BALANCES FOR USER: " + user.getId());

        HashMap<User, Float> map = balanceSheet.getUserBalances(user);

        if (map.isEmpty()) {
            System.out.println("No balances for this user.");
            return;
        }

        for (User other : map.keySet()) {
            float amt = map.get(other);

            if (amt > 0)
                System.out.println(user.getId() + " owes " + other.getId() + ": " + amt);
            else if (amt < 0)
                System.out.println(other.getId() + " owes " + user.getId() + ": " + (-amt));
            else
                System.out.println("Settled with " + other.getId());
        }
    }
}
