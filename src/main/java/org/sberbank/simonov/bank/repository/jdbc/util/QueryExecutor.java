package org.sberbank.simonov.bank.repository.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface QueryExecutor<T> {
    T execute(Connection connection) throws SQLException;
}