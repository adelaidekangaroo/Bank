package org.sberbank.simonov.bank.repository.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryExecutor<T> {
    boolean execute(Connection connection) throws SQLException;
}
