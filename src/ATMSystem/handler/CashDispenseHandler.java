package ATMSystem.handler;

import ATMSystem.model.WithdrawRequest;

public class CashDispenseHandler extends ATMHandler {

    private final DenominationHandler denominationChain =
            new FiveHundredDenominationHandler(
                    new TwoHundredDenominationHandler(
                            new HundredDenominationHandler(null)
                    )
            );

    public CashDispenseHandler( ATMHandler next) {
        super(next);
    }

    @Override
    public void handle(WithdrawRequest request) {
        // Phase-1 Check if possible
        denominationChain.canDispense(request);

        if (request.remainingAmount != 0) {
            System.out.println("❌ ATM cannot dispense exact amount");
            return;
        }
        // Phase-2 Dispensing
        System.out.println("✅ Dispensing cash: ₹" + request.amount);
        denominationChain.dispense(request);
    }
}
