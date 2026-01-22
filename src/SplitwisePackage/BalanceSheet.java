package SplitwisePackage;

import java.util.HashMap;
import java.util.List;

public class BalanceSheet {

    // Shared ledger: User -> (User -> Amount)
    HashMap<User, HashMap<User, Float>> balances;

    public BalanceSheet() {
        this.balances = new HashMap<>();
    }

    public void update(Expense expense) {
        List<Split> splits = expense.getSplits();
        User paidBy = expense.getPaidBy();
        balances.putIfAbsent(paidBy, new HashMap<>());

        for (Split split : splits) {
            User owesUser = split.getUser();
            balances.putIfAbsent(owesUser, new HashMap<>());
            float amount = split.getAmount();
            if (owesUser == paidBy) continue;
            HashMap<User, Float> row = balances.get(paidBy);
            row.put(owesUser, row.getOrDefault(owesUser, 0f) + amount);
        }
        for (Split split : splits) {
            User owesUser = split.getUser();
            balances.putIfAbsent(owesUser, new HashMap<>());
            float amount = split.getAmount();
            if (owesUser == paidBy) continue;
            HashMap<User, Float> row = balances.get(owesUser);
            row.put(paidBy, row.getOrDefault(paidBy, 0f) - amount);
        }
    }

    public HashMap<User, Float> getUserBalances(User user) {
        return balances.getOrDefault(user, new HashMap<>());
    }
}
