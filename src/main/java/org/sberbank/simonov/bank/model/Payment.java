package org.sberbank.simonov.bank.model;

import org.sberbank.simonov.bank.model.abstraction.ConfirmedEntity;

import java.math.BigDecimal;
import java.util.Objects;

public class Payment extends ConfirmedEntity {

    private final BigDecimal amount;
    private final int accountOwnerId;
    private final int counterpartyId;

    public Payment(BigDecimal amount, int accountOwnerId, int counterpartyId, boolean isConfirmed) {
        super(null, isConfirmed);
        this.amount = amount;
        this.accountOwnerId = accountOwnerId;
        this.counterpartyId = counterpartyId;
    }

    public Payment(Integer id, BigDecimal amount, int accountOwnerId, int counterpartyId, boolean isConfirmed) {
        super(id, isConfirmed);
        this.amount = amount;
        this.accountOwnerId = accountOwnerId;
        this.counterpartyId = counterpartyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getAccountOwnerId() {
        return accountOwnerId;
    }

    public int getCounterpartyId() {
        return counterpartyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Payment payment = (Payment) o;
        return accountOwnerId == payment.accountOwnerId &&
                counterpartyId == payment.counterpartyId &&
                Objects.equals(amount, payment.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount, accountOwnerId, counterpartyId);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", accountOwnerId=" + accountOwnerId +
                ", counterpartyId=" + counterpartyId +
                ", isConfirmed=" + isConfirmed +
                '}';
    }
}
