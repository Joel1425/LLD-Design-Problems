package ATMSystem.handler;

import ATMSystem.model.WithdrawRequest;

public abstract class ATMHandler {

    protected ATMHandler next;

    public ATMHandler(ATMHandler next) {
        this.next = next;
    }

    public abstract void handle(WithdrawRequest request);
}


