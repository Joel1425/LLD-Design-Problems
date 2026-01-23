package ATMSystem.ATM;

import java.util.HashMap;
import java.util.Map;

public class ATM {
    private static final ATM INSTANCE = new ATM();
    private Map<String, Float> balanceDB;
    ATM() {
        this.balanceDB = new HashMap<>();
    }

    public static ATM getInstance(){
        return INSTANCE;
    }

    public void addBalance(String user, float amount){
        if (amount <= 0) return;
        if (balanceDB.containsKey(user)){
            balanceDB.put(user, balanceDB.get(user)+amount);
        } else {
            balanceDB.put(user, amount);
        }
    }

}
