package ATMSystem.handler;

import ATMSystem.model.User;
import ATMSystem.model.WithdrawRequest;
import ATMSystem.repository.UserDB;

public class CardValidationHandler extends ATMHandler {

    public CardValidationHandler(ATMHandler next) {
        super(next);
    }

    @Override
    public void handle(WithdrawRequest request) {
        User user = UserDB.users.get(request.cardNumber);
        if (user == null) {
            System.out.println("❌ Invalid card");
            return;
        }
        request.user = user;
        next.handle(request);
    }
}
