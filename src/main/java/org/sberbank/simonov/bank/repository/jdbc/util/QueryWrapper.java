package org.sberbank.simonov.bank.repository.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

public class QueryWrapper {

    private QueryWrapper() {
    }

    public static <T> T wrap(QueryExecutor<T> executor, boolean isTransaction) {
        T result = null;
        try (Connection connection = DbConfig.getConnection()) {
            if (isTransaction) {
                try {
                    connection.setAutoCommit(false);
                    result = executor.execute(connection);
                    connection.commit();
                } finally {
                    connection.rollback();
                }
            } else {
                result = executor.execute(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
