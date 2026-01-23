package ATMSystem.handler;

import ATMSystem.model.WithdrawRequest;

public class BalanceCheckHandler extends ATMHandler {

    public BalanceCheckHandler(ATMHandler next) {
        super(next);
    }

    @Override
    public void handle(WithdrawRequest request) {
        if (request.amount > request.user.getBalance()) {
            System.out.println("❌ Insufficient balance");
            return;
        }
        request.user.deductBalance(request.amount);
        next.handle(request);
    }
}

