package ATMSystem.handler;

import ATMSystem.model.WithdrawRequest;

public abstract class DenominationHandler {
    protected DenominationHandler next;

    DenominationHandler( DenominationHandler next){
        this.next = next;
    }

    public abstract void canDispense(WithdrawRequest withdrawRequest);
    public abstract void dispense(WithdrawRequest withdrawRequest);
}
