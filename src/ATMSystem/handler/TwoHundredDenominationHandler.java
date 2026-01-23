package ATMSystem.handler;

import ATMSystem.model.WithdrawRequest;
import ATMSystem.repository.ATMCashRepository;

public class TwoHundredDenominationHandler extends DenominationHandler{
    TwoHundredDenominationHandler( DenominationHandler next){
        super(next);
    }

    @Override
    public void canDispense(WithdrawRequest withdrawRequest) {
        int remainingAmount = withdrawRequest.remainingAmount;
        int notesAvailableAtATM = ATMCashRepository.getCount(200);
        int maxCountAmountCanHold = remainingAmount/200;
        int deductionCount = Math.min( notesAvailableAtATM, maxCountAmountCanHold);
        withdrawRequest.remainingAmount -= deductionCount*200;

        if (next!=null){
            next.canDispense(withdrawRequest);
        }

    }

    @Override
    public void dispense(WithdrawRequest withdrawRequest) {
        int amount = withdrawRequest.amount;
        int notesAvailableAtATM = ATMCashRepository.getCount(200);
        int maxCountAmountCanHold = amount/200;
        int deductionCount = Math.min( notesAvailableAtATM, maxCountAmountCanHold);
        if (deductionCount > 0){
            System.out.println("₹200 x " + deductionCount);
            ATMCashRepository.deduct(200, deductionCount);
            withdrawRequest.amount -= deductionCount * 200;
        }
        if (next!=null){
            next.dispense(withdrawRequest);
        }
    }
}
