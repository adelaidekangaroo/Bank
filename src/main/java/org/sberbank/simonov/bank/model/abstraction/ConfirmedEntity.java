package org.sberbank.simonov.bank.model.abstraction;

import java.util.Objects;

public abstract class ConfirmedEntity extends BaseEntity {

    protected boolean isConfirmed = false;

    protected ConfirmedEntity(Integer id) {
        super(id);
    }

    protected ConfirmedEntity(Integer id, boolean isConfirmed) {
        super(id);
        this.isConfirmed = isConfirmed;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConfirmedEntity that = (ConfirmedEntity) o;
        return isConfirmed == that.isConfirmed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isConfirmed);
    }
}
