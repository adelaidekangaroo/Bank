package org.sberbank.simonov.bank.data;

import org.sberbank.simonov.bank.model.Card;

import static org.sberbank.simonov.bank.data.AccountTestData.*;

public class CardTestData {

    public static final Card CARD_1 = new Card(1, ACCOUNT_1.getId(), false, false, 1111111111111111L);
    public static final Card CARD_2 = new Card(2, ACCOUNT_2.getId(), true, false, 1234567890123456L);
    public static final Card CARD_3 = new Card(3, ACCOUNT_3.getId(), true, false, 3456786543211111L);
    public static final Card CARD_4 = new Card(4, ACCOUNT_4.getId(), true, false, 3456786543666666L);

    public static final int NEW_CARD_ID = CARD_4.getId() + 1;

    public static Card created() {
        return new Card(ACCOUNT_1.getId(), true, false, 4444444444444444L);
    }

    public static Card updated() {
        return new Card(CARD_1.getId(), CARD_1.getAccountId(), CARD_1.isActive(), true, CARD_1.getNumber());
    }
}
