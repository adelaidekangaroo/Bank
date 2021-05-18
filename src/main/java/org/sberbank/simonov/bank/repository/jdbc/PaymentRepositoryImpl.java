package org.sberbank.simonov.bank.repository.jdbc;

import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.repository.PaymentRepository;
import org.sberbank.simonov.bank.repository.jdbc.util.Parcelable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.sberbank.simonov.bank.repository.jdbc.util.QueryWrapper.*;

public class PaymentRepositoryImpl implements PaymentRepository, Parcelable<Payment> {

    @Override
    public boolean save(Payment payment) {
        if (payment.hasId()) {
            return saveWrap(statement -> {
                parseToStatement(statement, payment);
                statement.setInt(5, payment.getId());
            }, UPDATE);
        } else {
            return saveWrap(statement -> parseToStatement(statement, payment), INSERT);
        }
    }

    @Override
    public Payment getById(int id) {
        return getSingleWrap(statement -> statement.setInt(1, id), this, GET_BY_ID);
    }

    @Override
    public List<Payment> getAllUnconfirmed() {
        return getListWrap(statement -> {
        }, this, GET_ALL_UNCONFIRMED);
    }

    @Override
    public Payment parseFromResultSet(ResultSet resultSet) throws SQLException {
        return new Payment(
                resultSet.getInt("id"),
                resultSet.getBigDecimal("amount"),
                resultSet.getInt("account_owner_id"),
                resultSet.getInt("counterparty_id"),
                resultSet.getBoolean("is_confirmed")
        );
    }

    @Override
    public PreparedStatement parseToStatement(PreparedStatement statement, Payment object) throws SQLException {
        statement.setBigDecimal(1, object.getAmount());
        statement.setInt(2, object.getAccountOwnerId());
        statement.setInt(3, object.getCounterpartyId());
        statement.setBoolean(4, object.isConfirmed());
        return statement;
    }
}
