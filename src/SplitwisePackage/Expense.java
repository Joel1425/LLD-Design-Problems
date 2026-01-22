package SplitwisePackage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String id;
    private float amount;
    private User paidBy;
    private List<User> participants;
    private Group group;
    private List<Split> splits;
    private SplitStrategy splitStrategy;
    private String remark;
    private Instant createdAt;
    public Expense( String id, float amount, User paidBy, List<User> participants, Group group,
                    SplitStrategy splitStrategy, String remark ) {
        this.id = id;
        this.amount = amount;
        this.paidBy = paidBy;
        this.participants = participants;
        this.group = group;
        this.splitStrategy = splitStrategy;
        this.remark = remark;
        this.splits = new ArrayList<>();
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void calculateSplits(){
        this.splits = this.splitStrategy.getSplit( this );
    }

    public void printExpenseLog(){
        System.out.println("+++++++++++++++++++++EXPENSE LOG+++++++++++++++++++++++");
        System.out.println("ID: "+this.id+"\nAmount: "+this.amount+"\nPaid By: "+this.paidBy.getId()+"\nCreated At: "+this.createdAt);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
