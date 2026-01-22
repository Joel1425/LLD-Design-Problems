package SplitwisePackage;

import java.util.ArrayList;
import java.util.List;

public class User {
    String id;
    List<User> friends;
    List<Group> groups;
    GroupService groupService;
    ExpenseService expenseService;
    private BalanceService balanceService;
    public User(String id) {
        this.id = id;
        this.friends = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.groupService = new GroupService();
        this.expenseService = new ExpenseService();
        this.balanceService = new BalanceService();
    }

    public String getId(){
        return this.id;
    }

    public void addFriend( User user){
        this.friends.add(user);
        user.friends.add(this);
    }

    public Group createGroup( String groupId, List<User> users ){
        Group group = this.groupService.createGroup( groupId, users );
        System.out.println( "Group: " + group.getId() + " CREATED ");
        group.printMembers();
        this.groups.add(group);
        return group;
    }

    public void printBalances(){
        this.balanceService.printBalances(this);
    }

    public void createExpense( String id, float amount, User paidBy, List<User> participants, Group group,
                                  SplitStrategy splitStrategy, String remark ){
        expenseService.createExpense( id, amount, paidBy, participants, group, splitStrategy, remark );
    }

    public void addMemberToGroup( Group group, User user ){
        this.groupService.addMemberToGroup( group, user );
    }

    public void printFriends(){
        System.out.println("FRIENDS OF USER - "+ this.id);
        for (User user: this.friends){
            System.out.println( user.id );
        }
    }

}
