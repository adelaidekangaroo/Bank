package org.sberbank.simonov.bank.model;

import org.sberbank.simonov.bank.model.abstraction.BaseEntity;

import java.math.BigDecimal;
import java.util.Objects;

public class Account extends BaseEntity {

    private final int userId;
    private BigDecimal amount = BigDecimal.valueOf(0, 0);

    public Account(int userId) {
        super(null);
        this.userId = userId;
    }

    public Account(int userId, BigDecimal amount) {
        super(null);
        this.userId = userId;
        this.amount = amount;
    }

    public Account(Integer id, int userId, BigDecimal amount) {
        super(id);
        this.userId = userId;
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return userId == account.userId &&
                Objects.equals(amount, account.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", amount=" + amount +
                ", id=" + id +
                '}';
    }
}
