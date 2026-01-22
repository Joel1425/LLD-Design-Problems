package SplitwisePackage;

import java.util.ArrayList;
import java.util.List;

public class Splitwise {
    public static void main(String[] args) {
        User joel = new User("Joel");
        User andria = new User("Andria");
        joel.addFriend(andria);
        User satyam = new User("Satyam");
        User sameer = new User("Sameer");
        joel.addFriend(sameer);
        joel.addFriend(satyam);
        joel.printFriends();
        joel.createExpense("Cake", 500, joel, new ArrayList<>(List.of(joel, sameer, satyam)),
                                                null, new EqualSplitStrategy(), "Amount for Cake");
        joel.printBalances();

        Group trio = joel.createGroup("TRIO", new ArrayList<>(List.of(joel, sameer, satyam)));
        joel.createExpense("Biscuits", 300, joel, new ArrayList<>(List.of(joel, sameer, satyam)),
                trio, new EqualSplitStrategy(), "Amount for Biscuits");
        joel.printBalances();
        sameer.printBalances();
        satyam.printBalances();

        joel.addMemberToGroup(trio, andria);
        joel.createExpense("Fruits", 400, joel, new ArrayList<>(List.of(joel, sameer, satyam)),
                trio, new EqualSplitStrategy(), "Amount for Biscuits");



    }
}
