package ATMSystem.repository;

import java.util.HashMap;
import java.util.Map;

public class ATMCashRepository {

    private static final Map<Integer, Integer> cashInventory = new HashMap<>();

    static {
        cashInventory.put(500, 8);  // 8 notes of 500
        cashInventory.put(200, 10);  // 10 notes of 200
        cashInventory.put(100, 20);  // 20 notes of 100
    }

    public static int getCount( int denomination ) {
        return cashInventory.getOrDefault(denomination, 0);
    }

    public static void deduct( int denomination, int count ){
        cashInventory.put(denomination, cashInventory.get(denomination)-count);
    }

}
