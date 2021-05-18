package org.sberbank.simonov.bank.repository.jdbc;

import org.sberbank.simonov.bank.model.Payment;
import org.sberbank.simonov.bank.repository.AccountRepository;
import org.sberbank.simonov.bank.repository.PaymentRepository;
import org.sberbank.simonov.bank.repository.jdbc.util.Parcelable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.sberbank.simonov.bank.repository.jdbc.util.QueryWrapper.*;

public class PaymentRepositoryImpl implements PaymentRepository, Parcelable<Payment> {

    private final AccountRepository accountRepository = new AccountRepositoryImpl();

    @Override
    public boolean create(Payment payment, int userId) {
        if (accountRepository.getById(payment.getAccountOwnerId(), userId) != null) {
            return saveWrap(statement -> parseToStatement(statement, payment), INSERT);
        } else {
            return false;
        }
    }

    @Override
    public boolean confirmPayment(Payment payment) {
        return wrap(connection -> {
            boolean isExecuted;
            try (PreparedStatement ownerUpdateStatement = connection.prepareStatement(AccountRepository.UPDATE_WRITE_OFF)) {
                ownerUpdateStatement.setBigDecimal(1, payment.getAmount());
                ownerUpdateStatement.setBigDecimal(2, payment.getAmount());
                ownerUpdateStatement.setInt(3, payment.getAccountOwnerId());
                isExecuted = ownerUpdateStatement.executeUpdate() > 0;
            }
            try (PreparedStatement conterpartyUpdateStatement = connection.prepareStatement(AccountRepository.UPDATE_REPLENISHMENT)) {
                conterpartyUpdateStatement.setBigDecimal(1, payment.getAmount());
                conterpartyUpdateStatement.setInt(2, payment.getCounterpartyId());
                isExecuted = isExecuted && conterpartyUpdateStatement.executeUpdate() > 0;
            }
            try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
                statement.setInt(1, payment.getId());
                isExecuted = isExecuted && statement.executeUpdate() > 0;
            }
            return isExecuted;
        }, true);
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
    public void parseToStatement(PreparedStatement statement, Payment object) throws SQLException {
        statement.setBigDecimal(1, object.getAmount());
        statement.setInt(2, object.getAccountOwnerId());
        statement.setInt(3, object.getCounterpartyId());
        statement.setBoolean(4, object.isConfirmed());
    }
}
