package org.sberbank.simonov.bank.repository.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Parcelable<T> {

    T parseFromResultSet(ResultSet resultSet) throws SQLException;

    PreparedStatement parseToStatement(PreparedStatement statement, T object) throws SQLException;
}
