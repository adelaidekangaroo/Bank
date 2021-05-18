package org.sberbank.simonov.bank.repository.jdbc;

import org.sberbank.simonov.bank.model.Card;
import org.sberbank.simonov.bank.repository.CardRepository;
import org.sberbank.simonov.bank.repository.jdbc.util.Parcelable;
import org.sberbank.simonov.bank.repository.jdbc.util.QueryWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository, Parcelable<Card> {

    @Override
    public boolean save(Card card) {
        return QueryWrapper.wrap(connection -> {
            boolean isExecuted;
            if (card.hasId()) {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
                    parseToStatement(statement, card);
                    statement.setInt(5, card.getId());
                    isExecuted = statement.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
                    parseToStatement(statement, card);
                    isExecuted = statement.executeUpdate() > 0;
                }
            }
            return isExecuted;
        }, true);
    }

    @Override
    public Card getById(int id) {
        return QueryWrapper.wrap(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
                statement.setInt(1, id);
                Card result = null;
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
    public List<Card> getAllUnconfirmed() {
        return QueryWrapper.wrap(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_UNCONFIRMED)) {
                List<Card> result = new ArrayList<>();
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
    public List<Card> getAllByUser(int userId) {
        return QueryWrapper.wrap(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_USER)) {
                statement.setInt(1, userId);
                List<Card> result = new ArrayList<>();
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
    public Card parseFromResultSet(ResultSet resultSet) throws SQLException {
        return new Card(
                resultSet.getInt("id"),
                resultSet.getInt("account_id"),
                resultSet.getBoolean("is_active"),
                resultSet.getBoolean("is_confirmed"),
                resultSet.getLong("number")
        );
    }

    @Override
    public PreparedStatement parseToStatement(PreparedStatement statement, Card object) throws SQLException {
        statement.setInt(1, object.getAccountId());
        statement.setBoolean(2, object.isActive());
        statement.setBoolean(3, object.isConfirmed());
        statement.setLong(4, object.getNumber());
        return statement;
    }
}
