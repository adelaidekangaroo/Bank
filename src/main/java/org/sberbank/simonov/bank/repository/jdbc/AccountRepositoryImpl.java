package org.sberbank.simonov.bank.repository.jdbc;

import org.sberbank.simonov.bank.model.Account;
import org.sberbank.simonov.bank.repository.AccountRepository;
import org.sberbank.simonov.bank.repository.jdbc.util.Parcelable;
import org.sberbank.simonov.bank.repository.jdbc.util.QueryWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository, Parcelable<Account> {

    @Override
    public boolean save(Account account, int userId) {
        return QueryWrapper.wrap(connection -> {
            boolean isExecuted;
            if (account.hasId()) {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
                    statement.setBigDecimal(1, account.getAmount());
                    statement.setInt(2, account.getId());
                    statement.setInt(3, userId);
                    isExecuted = statement.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
                    statement.setInt(1, userId);
                    statement.setBigDecimal(2, account.getAmount());
                    isExecuted = statement.executeUpdate() > 0;
                }
            }
            return isExecuted;
        }, true);
    }

    @Override
    public Account getById(int id, int userId) {
        return QueryWrapper.wrap(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
                statement.setInt(1, id);
                statement.setInt(2, userId);
                Account result = null;
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
    public List<Account> getAllByUser(int userId) {
        return QueryWrapper.wrap(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_USER)) {
                statement.setInt(1, userId);
                List<Account> result = new ArrayList<>();
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
    public Account parseFromResultSet(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getBigDecimal("amount")
        );
    }

    @Override
    public PreparedStatement parseToStatement(PreparedStatement statement, Account object) throws SQLException {
        return statement;
    }
}