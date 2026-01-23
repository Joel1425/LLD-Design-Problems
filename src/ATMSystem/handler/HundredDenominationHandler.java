package ATMSystem.handler;

import ATMSystem.model.WithdrawRequest;
import ATMSystem.repository.ATMCashRepository;

public class HundredDenominationHandler extends DenominationHandler{
    HundredDenominationHandler( DenominationHandler next){
        super(next);
    }

    @Override
    public void canDispense(WithdrawRequest withdrawRequest) {
        int remainingAmount = withdrawRequest.remainingAmount;
        int notesAvailableAtATM = ATMCashRepository.getCount(100);
        int maxCountAmountCanHold = remainingAmount/100;
        int deductionCount = Math.min( notesAvailableAtATM, maxCountAmountCanHold);
        withdrawRequest.remainingAmount -= deductionCount*100;


        if (next!=null){
            next.canDispense(withdrawRequest);
        }

    }

    @Override
    public void dispense(WithdrawRequest withdrawRequest) {
        int amount = withdrawRequest.amount;
        int notesAvailableAtATM = ATMCashRepository.getCount(100);
        int maxCountAmountCanHold = amount/100;
        int deductionCount = Math.min( notesAvailableAtATM, maxCountAmountCanHold);
        if (deductionCount > 0){
            System.out.println("₹100 x " + deductionCount);
            ATMCashRepository.deduct(100, deductionCount);
            withdrawRequest.amount -= deductionCount * 100;
        }
        if (next!=null){
            next.dispense(withdrawRequest);
        }
    }
}
