package SplitwisePackage;

import java.util.List;

public class ExpenseService {

    private final BalanceService balanceService = new BalanceService();

    public Expense createExpense(String id,
                                 float amount,
                                 User paidBy,
                                 List<User> participants,
                                 Group group,
                                 SplitStrategy splitStrategy,
                                 String remark) {

        // 1. Create expense
        Expense expense = new Expense(
                id, amount, paidBy, participants, group, splitStrategy, remark
        );

        // 2. Calculate splits via strategy
        expense.calculateSplits();

        // 3. Add expense to group if applicable
        if (group != null) {
            group.addExpense(expense);
        }

        // 4. Update balances
        balanceService.updateBalances(expense);

        System.out.print("CREATED--> ");
        expense.printExpenseLog();
        return expense;
    }
}
