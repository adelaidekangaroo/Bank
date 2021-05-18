package org.sberbank.simonov.bank.repository.jdbc;

import org.sberbank.simonov.bank.model.Account;
import org.sberbank.simonov.bank.repository.AccountRepository;
import org.sberbank.simonov.bank.repository.jdbc.util.Parcelable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.sberbank.simonov.bank.repository.jdbc.util.QueryWrapper.*;

public class AccountRepositoryImpl implements AccountRepository, Parcelable<Account> {

    @Override
    public boolean save(Account account, int userId) {
        if (account.hasId()) {
            return saveWrap(statement -> {
                statement.setBigDecimal(1, account.getAmount());
                statement.setInt(2, account.getId());
                statement.setInt(3, userId);
            }, UPDATE);
        } else {
            return saveWrap(statement -> {
                statement.setInt(1, userId);
                statement.setBigDecimal(2, account.getAmount());
            }, INSERT);
        }
    }

    @Override
    public Account getById(int id, int userId) {
        return getSingleWrap(statement -> {
            statement.setInt(1, id);
            statement.setInt(2, userId);
        }, this, GET_BY_ID);
    }

    @Override
    public List<Account> getAllByUser(int userId) {
        return getListWrap(statement -> statement.setInt(1, userId), this, GET_ALL_BY_USER);
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
    public void parseToStatement(PreparedStatement statement, Account object) throws SQLException {
    }
}