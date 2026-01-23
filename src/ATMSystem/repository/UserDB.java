package ATMSystem.repository;

import ATMSystem.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDB {
    public static final Map<String, User> users = new HashMap<>();

    static {
        users.put("1234", new User("1234", 1111, 6500));
        users.put("5678", new User("5678", 2222, 3000));
    }

}

