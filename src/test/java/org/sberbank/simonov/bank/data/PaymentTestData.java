package org.sberbank.simonov.bank.data;

import org.sberbank.simonov.bank.model.Payment;

import java.math.BigDecimal;

import static java.math.RoundingMode.DOWN;
import static org.sberbank.simonov.bank.data.AccountTestData.ACCOUNT_2;
import static org.sberbank.simonov.bank.data.AccountTestData.ACCOUNT_3;

public class PaymentTestData {

    public static final Payment PAYMENT_1 = new Payment(1, BigDecimal.valueOf(100).setScale(2, DOWN), ACCOUNT_2.getId(), ACCOUNT_3.getId(), false);
    public static final Payment PAYMENT_2 = new Payment(2, BigDecimal.valueOf(1000).setScale(2, DOWN), ACCOUNT_3.getId(), ACCOUNT_2.getId(), false);

    public static final int NEW_PAYMENT_ID = PAYMENT_2.getId() + 1;

    public static Payment created() {
        return new Payment(BigDecimal.valueOf(200).setScale(2, DOWN), ACCOUNT_2.getId(), ACCOUNT_3.getId(), false);
    }

    public static Payment updated() {
        return new Payment(PAYMENT_1.getId(), PAYMENT_1.getAmount(), PAYMENT_1.getAccountOwnerId(), PAYMENT_1.getCounterpartyId(), true);
    }
}
