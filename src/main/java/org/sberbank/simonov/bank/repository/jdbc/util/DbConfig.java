package org.sberbank.simonov.bank.repository.jdbc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class DbConfig {

    private static final String DATABASE_DRIVER = "org.h2.Driver";
    private static final String DATABASE_URL = "jdbc:h2:mem:bank;DB_CLOSE_DELAY=-1";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    private DbConfig() {
    }

    public static void initDb() {
        try {
            String init = new String(Files.readAllBytes(Paths.get("src/main/resources/db/init.sql")))
            + new String(Files.readAllBytes(Paths.get("src/main/resources/db/populate.sql")));
            executeScript(init);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
