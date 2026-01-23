package ATMSystem;

import ATMSystem.handler.*;
import ATMSystem.model.WithdrawRequest;

public class Main {

    public static void main(String[] args) {

        // 1️⃣ Build ATM chain (order matters)
        ATMHandler atmChain =
                new CardValidationHandler(
                        new PinValidationHandler(
                                new BalanceCheckHandler(
                                        new CashDispenseHandler(null)
                                )
                        )
                );

        // 2️⃣ Valid withdrawal
        System.out.println("---- Case 1: Valid Withdrawal ----");
        WithdrawRequest request1 =
                new WithdrawRequest("1234", 1111, 3300);
        atmChain.handle(request1);

        // 3️⃣ Invalid PIN
        System.out.println("\n---- Case 2: Invalid PIN ----");
        WithdrawRequest request2 =
                new WithdrawRequest("1234", 9999, 1000);
        atmChain.handle(request2);

        // 4️⃣ Insufficient Balance
        System.out.println("\n---- Case 3: Insufficient Balance ----");
        WithdrawRequest request3 =
                new WithdrawRequest("5678", 2222, 5000);
        atmChain.handle(request3);

        // 5️⃣ Invalid Card
        System.out.println("\n---- Case 4: Invalid Card ----");
        WithdrawRequest request4 =
                new WithdrawRequest("9999", 1111, 500);
        atmChain.handle(request4);

        // 6️⃣ Another valid withdrawal (shows balance deduction)
        System.out.println("\n---- Case 5: Second Withdrawal Same User ----");
        WithdrawRequest request5 =
                new WithdrawRequest("1234", 1111, 2500);
        atmChain.handle(request5);

        // 7️⃣ Another valid withdrawal (shows balance deduction)
        System.out.println("\n---- Case 6: Third Withdrawal Same User ----");
        WithdrawRequest request6 =
                new WithdrawRequest("1234", 1111, 500);
        atmChain.handle(request6);
    }
}

