package org.sberbank.simonov.bank.repository.jdbc.util;

import org.sberbank.simonov.bank.model.abstraction.BaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean saveWrap(FillingStatement executor, String sql) {
        boolean isExecuted = false;
        try (Connection connection = DbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                executor.state(statement);
                isExecuted = statement.executeUpdate() > 0;
                connection.commit();
            } finally {
                connection.rollback();
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return isExecuted;
    }

    public static <T extends BaseEntity> T getSingleWrap(FillingStatement executor, Parcelable<T> parcelable, String sql) {
        T result = null;
        try (Connection connection = DbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            executor.state(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                executor.state(statement);
                if (resultSet.next()) {
                    result = parcelable.parseFromResultSet(resultSet);
                }
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T extends BaseEntity> List<T> getListWrap(FillingStatement executor, Parcelable<T> parcelable, String sql) {
        List<T> result = new ArrayList<>();
        try (Connection connection = DbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            executor.state(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                executor.state(statement);
                while (resultSet.next()) {
                    result.add(parcelable.parseFromResultSet(resultSet));
                }
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

