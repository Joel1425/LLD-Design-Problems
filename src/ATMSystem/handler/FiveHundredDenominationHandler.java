package ATMSystem.handler;

import ATMSystem.model.WithdrawRequest;
import ATMSystem.repository.ATMCashRepository;

public class FiveHundredDenominationHandler extends DenominationHandler{
    FiveHundredDenominationHandler( DenominationHandler next){
        super(next);
    }

    @Override
    public void canDispense(WithdrawRequest withdrawRequest) {
        int remainingAmount = withdrawRequest.remainingAmount;
        int notesAvailableAtATM = ATMCashRepository.getCount(500);
        int maxCountAmountCanHold = remainingAmount/500;
        int deductionCount = Math.min( notesAvailableAtATM, maxCountAmountCanHold);
        withdrawRequest.remainingAmount -= deductionCount*500;

        if (next!=null){
            next.canDispense(withdrawRequest);
        }

    }

    @Override
    public void dispense(WithdrawRequest withdrawRequest) {
        int amount = withdrawRequest.amount;
        int notesAvailableAtATM = ATMCashRepository.getCount(500);
        int maxCountAmountCanHold = amount/500;
        int deductionCount = Math.min( notesAvailableAtATM, maxCountAmountCanHold);
        if (deductionCount > 0){
            System.out.println("₹500 x " + deductionCount);
            ATMCashRepository.deduct(500, deductionCount);
            withdrawRequest.amount -= deductionCount * 500;
        }
        if (next!=null){
            next.dispense(withdrawRequest);
        }
    }
}
