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

    private final QueryWrapper wrapper = new QueryWrapper();

    @Override
    public boolean save(Card card) {
        return wrapper.wrap(connection -> {
            boolean isExecuted;
            if (card.hasId()) {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
                    statement.setInt(1, card.getAccountId());
                    statement.setBoolean(2, card.isActive());
                    statement.setBoolean(3, card.isConfirmed());
                    statement.setLong(4, card.getNumber());
                    statement.setInt(5, card.getId());
                    isExecuted = statement.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
                    statement.setInt(1, card.getAccountId());
                    statement.setBoolean(2, card.isActive());
                    statement.setBoolean(3, card.isConfirmed());
                    statement.setLong(4, card.getNumber());
                    isExecuted = statement.executeUpdate() > 0;
                }
            }
            return isExecuted;
        }, true);
    }

    @Override
    public Card getById(int id) {
        return wrapper.wrap(connection -> {
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
        return wrapper.wrap(connection -> {
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
        return wrapper.wrap(connection -> {
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
        return null;
    }
}
