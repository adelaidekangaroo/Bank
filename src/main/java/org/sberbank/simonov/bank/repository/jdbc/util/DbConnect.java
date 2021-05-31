package org.sberbank.simonov.bank.repository.jdbc.util;

import org.apache.maven.model.Model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class DbConnect {

    private final ConnectionFactory connectionFactory;
    private final String initScript;
    private final String populateScript;

    public DbConnect(
            final String url,
            final String username,
            final String password,
            final String driver,
            final String init,
            final String populate) {

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        this.connectionFactory = () -> DriverManager.getConnection(url, username, password);
        this.initScript = init;
        this.populateScript = populate;
    }

    public void initDb() {
        InputStream initStream = Model.class.getClassLoader().getResourceAsStream(initScript);
        InputStream populateStream = Model.class.getClassLoader().getResourceAsStream(populateScript);

        String init = new BufferedReader(new InputStreamReader(initStream)).lines().collect(Collectors.joining());
        String populate = new BufferedReader(new InputStreamReader(populateStream)).lines().collect(Collectors.joining());

        executeScript(init + populate);
    }

    public void executeScript(String script) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(script)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return connectionFactory.getConnection();
    }
}
