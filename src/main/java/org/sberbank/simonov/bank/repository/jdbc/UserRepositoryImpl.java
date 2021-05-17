package org.sberbank.simonov.bank.repository.jdbc;

import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.model.UserType;
import org.sberbank.simonov.bank.repository.UserRepository;
import org.sberbank.simonov.bank.repository.jdbc.util.Parcelable;
import org.sberbank.simonov.bank.repository.jdbc.util.QueryWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository, Parcelable<User> {

    private final QueryWrapper wrapper = new QueryWrapper();

    @Override
    public boolean save(User user) {
        return wrapper.wrap(connection -> {
            boolean isExecuted;
            if (user.hasId()) {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
                    parseToStatement(statement, user)
                            .setInt(6, user.getId());
                    isExecuted = statement.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
                    parseToStatement(statement, user);
                    isExecuted = statement.executeUpdate() > 0;
                }
            }
            return isExecuted;
        }, true);
    }

    @Override
    public List<User> getAllCounterparties(int currentUserId) {
        return wrapper.wrap(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_COUNTERPARTIES)) {
                statement.setInt(1, currentUserId);
                List<User> result = new ArrayList<>();
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
    public User getById(int id) {
        return wrapper.wrap(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
                statement.setInt(1, id);
                User result = null;
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
    public PreparedStatement parseToStatement(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getFullName());
        statement.setString(4, user.getRole().toString());
        statement.setString(5, user.getUserType().toString());
        return statement;
    }
}
