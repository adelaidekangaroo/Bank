package org.sberbank.simonov.bank.repository.jdbc;

import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.repository.PaymentRepository;
import org.sberbank.simonov.bank.repository.jdbc.util.Parcelable;
import org.sberbank.simonov.bank.repository.jdbc.util.QueryWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepositoryImpl implements PaymentRepository, Parcelable<Payment> {

    private final QueryWrapper wrapper = new QueryWrapper();

    @Override
    public boolean save(Payment payment) {
        return wrapper.wrap(connection -> {
            boolean isExecuted;
            if (payment.hasId()) {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
                    parseToStatement(statement, payment);
                    statement.setInt(5, payment.getId());
                    isExecuted = statement.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
                    parseToStatement(statement, payment);
                    isExecuted = statement.executeUpdate() > 0;
                }
            }
            return isExecuted;
        }, true);
    }

    @Override
    public Payment getById(int id) {
        return wrapper.wrap(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
                statement.setInt(1, id);
                Payment result = null;
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result = parseFromResultSet(resultSet);
                    }
                }
                return result;
            }
        }, false);
    }

    @Override
    public List<Payment> getAllUnconfirmed() {
        return wrapper.wrap(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_UNCONFIRMED)) {
                List<Payment> result = new ArrayList<>();
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(parseFromResultSet(resultSet));
                    }
                }
                return result;
            }
        }, false);
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
