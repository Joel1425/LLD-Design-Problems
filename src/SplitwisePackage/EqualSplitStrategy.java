package SplitwisePackage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class EqualSplitStrategy implements SplitStrategy{

    @Override
    public List<Split> getSplit(Expense expense) {
        List<Split> splits = new ArrayList<>();
        int n = expense.getParticipants().size();
        float amount = expense.getAmount();
        float splitAmount = amount/n;
        User paidBy = expense.getPaidBy();
        for (User user: expense.getParticipants()){
            if (user != paidBy){
                splits.add(new Split(user, -splitAmount));
            }
        }
        return splits;
    }
}
