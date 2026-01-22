package SplitwisePackage;

import java.util.List;

public interface SplitStrategy {
    public List<Split> getSplit(Expense expense );
}
