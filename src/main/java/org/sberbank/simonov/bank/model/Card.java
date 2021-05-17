package org.sberbank.simonov.bank.model;

import org.sberbank.simonov.bank.model.abstraction.ConfirmedEntity;

import java.util.Objects;

public class Card extends ConfirmedEntity {

    private final int accountId;
    private boolean isActive = false;
    private final long number;

    public Card(int accountId, long number) {
        super(null);
        this.accountId = accountId;
        this.number = number;
    }

    public Card(int accountId, boolean isActive, boolean isConfirmed, long number) {
        super(null, isConfirmed);
        this.accountId = accountId;
        this.isActive = isActive;
        this.number = number;
    }

    public Card(Integer id, int accountId, boolean isActive, boolean isConfirmed, long number) {
        super(id, isConfirmed);
        this.accountId = accountId;
        this.isActive = isActive;
        this.number = number;
    }

    public int getAccountId() {
        return accountId;
    }

    public boolean isActive() {
        return isActive;
    }

    public long getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return accountId == card.accountId &&
                isActive == card.isActive &&
                number == card.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountId, isActive, number);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", isActive=" + isActive +
                ", isConfirmed=" + isConfirmed +
                ", number=" + number +
                '}';
    }
}
