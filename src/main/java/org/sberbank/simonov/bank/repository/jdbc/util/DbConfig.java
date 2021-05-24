package org.sberbank.simonov.bank.repository.jdbc.util;

import org.apache.maven.model.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

public class DbConfig {

    private static final String DATABASE_DRIVER = "org.h2.Driver";
    private static final String DATABASE_URL = "jdbc:h2:mem:bank;DB_CLOSE_DELAY=-1";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    private DbConfig() {
    }

    public static void initDb() {
        String init = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(Model.class.getClassLoader().getResourceAsStream("db/init.sql"))
                )
        ).lines().collect(Collectors.joining());
        String populate = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(Model.class.getClassLoader().getResourceAsStream("db/populate.sql"))
                )
        ).lines().collect(Collectors.joining());

        executeScript(init + populate);
    }

    public static void executeScript(String script) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(script)) {

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Class.forName(DATABASE_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(connection);
    }
}
