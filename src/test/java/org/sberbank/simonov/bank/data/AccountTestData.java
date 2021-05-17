package org.sberbank.simonov.bank.data;

import org.sberbank.simonov.bank.model.Account;

import java.math.BigDecimal;

import static org.sberbank.simonov.bank.data.UserTestData.*;

public class AccountTestData {

    public static final Account ACCOUNT_1 = new Account(1, USER_1.getId(), BigDecimal.valueOf(0).setScale(2));
    public static final Account ACCOUNT_2 = new Account(2, USER_1.getId(), BigDecimal.valueOf(10000).setScale(2));
    public static final Account ACCOUNT_3 = new Account(3, USER_2.getId(), BigDecimal.valueOf(50000).setScale(2));
    public static final Account ACCOUNT_4 = new Account(4, USER_3.getId(), BigDecimal.valueOf(400000).setScale(2));

    public static final int NEW_ACCOUNT_ID = ACCOUNT_4.getId() + 1;

    public static Account created() {
        return new Account(USER_1.getId(), BigDecimal.valueOf(500).setScale(2));
    }

    public static Account updated() {
        return new Account(ACCOUNT_2.getId(), ACCOUNT_2.getUserId(), BigDecimal.valueOf(5000).setScale(2));
    }
}
