package ATMSystem.handler;

import ATMSystem.model.WithdrawRequest;

public class PinValidationHandler extends ATMHandler {

    public PinValidationHandler(ATMHandler next) {
        super(next);
    }

    @Override
    public void handle(WithdrawRequest request) {
        if (request.user.getPin() != request.pin) {
            System.out.println("❌ Invalid PIN");
            return;
        }
        next.handle(request);
    }
}
