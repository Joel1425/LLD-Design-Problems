package SplitwisePackage;

import java.util.ArrayList;
import java.util.List;

public class Group {
    String id;
    List<User> members;
    List<Expense> expenses;

    public Group(String id, List<User> members) {
        this.id = id;
        this.members = members;
        this.expenses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addMember(User user ){
        this.members.add(user);
    }

    public void addExpense( Expense expense ){
        this.expenses.add(expense);
    }

    public void printExpenses(){
        System.out.println("GROUP EXPENSES FOR "+ this.id);
        for (Expense expense: this.expenses){
            expense.printExpenseLog();
        }
    }

    public void printMembers(){
        System.out.println("GROUP MEMBERS FOR "+this.id);
        for (User user: this.members){
            System.out.println(user.getId());
        }
    }

}
