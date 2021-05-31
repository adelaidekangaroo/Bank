package org.sberbank.simonov.bank.config;

import org.sberbank.simonov.bank.repository.jdbc.util.DbConnect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfig extends AbstractConfig {

    private static final File DB_PROPS = getPropsFile("db/h2.properties");
    private static final DbConfig INSTANCE = new DbConfig();

    private final DbConnect connect;

    public static DbConfig get() {
        return INSTANCE;
    }

    private DbConfig() {
        try (InputStream isDb = new FileInputStream(DB_PROPS)) {
            Properties dbProperties = new Properties();
            dbProperties.load(isDb);
            connect = new DbConnect(
                    dbProperties.getProperty("db.url"),
                    dbProperties.getProperty("db.user"),
                    dbProperties.getProperty("db.password"),
                    dbProperties.getProperty("db.driver"),
                    dbProperties.getProperty("db.init"),
                    dbProperties.getProperty("db.populate")

            );
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + DB_PROPS);
        }
    }

    public DbConnect getConnect() {
        return connect;
    }
}
