package org.sberbank.simonov.bank.repository.jdbc;

import org.sberbank.simonov.bank.model.Card;
import org.sberbank.simonov.bank.repository.CardRepository;
import org.sberbank.simonov.bank.repository.jdbc.util.Parcelable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.sberbank.simonov.bank.repository.jdbc.util.QueryWrapper.*;

public class CardRepositoryImpl implements CardRepository, Parcelable<Card> {

    @Override
    public boolean save(Card card) {
        if (card.hasId()) {
            return saveWrap(statement -> {
                parseToStatement(statement, card);
                statement.setInt(5, card.getId());
            }, UPDATE);
        } else {
            return saveWrap(statement -> parseToStatement(statement, card), INSERT);
        }
    }

    @Override
    public Card getById(int id) {
        return getSingleWrap(statement -> statement.setInt(1, id), this, GET_BY_ID);
    }

    @Override
    public List<Card> getAllUnconfirmed() {
        return getListWrap(statement -> {
        }, this, GET_ALL_UNCONFIRMED);
    }

    @Override
    public List<Card> getAllByUser(int userId) {
        return getListWrap(statement -> statement.setInt(1, userId), this, GET_ALL_BY_USER);
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
