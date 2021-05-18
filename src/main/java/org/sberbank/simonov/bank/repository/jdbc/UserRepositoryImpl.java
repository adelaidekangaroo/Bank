package org.sberbank.simonov.bank.repository.jdbc;

import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.model.UserType;
import org.sberbank.simonov.bank.repository.UserRepository;
import org.sberbank.simonov.bank.repository.jdbc.util.Parcelable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.sberbank.simonov.bank.repository.jdbc.util.QueryWrapper.*;

public class UserRepositoryImpl implements UserRepository, Parcelable<User> {

    @Override
    public boolean save(User user) {
        if (user.hasId()) {
            return saveWrap(statement -> {
                parseToStatement(statement, user);
                statement.setInt(6, user.getId());
            }, UPDATE);
        } else {
            return saveWrap(statement -> parseToStatement(statement, user), INSERT);
        }
    }

    @Override
    public List<User> getAllCounterparties(int currentUserId) {
        return getListWrap(statement -> statement.setInt(1, currentUserId), this, GET_ALL_COUNTERPARTIES);
    }

    @Override
    public User getById(int id) {
        return getSingleWrap(statement -> statement.setInt(1, id), this, GET_BY_ID);
    }

    @Override
    public User parseFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("full_name"),
                Role.valueOf(resultSet.getString("role")),
                UserType.valueOf(resultSet.getString("user_type"))
        );
    }

    @Override
    public void parseToStatement(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getFullName());
        statement.setString(4, user.getRole().toString());
        statement.setString(5, user.getUserType().toString());
    }
}
