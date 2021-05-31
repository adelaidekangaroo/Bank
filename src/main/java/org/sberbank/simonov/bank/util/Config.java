package org.sberbank.simonov.bank.util;

import org.sberbank.simonov.bank.repository.jdbc.util.DbConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config extends AbstractConfig {

    private static final File DB_PROPS = getPropsFile("db/h2.properties");
    private static final Config INSTANCE = new Config();

    private final DbConfig storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream isDb = new FileInputStream(DB_PROPS)) {
            Properties dbProperties = new Properties();
            dbProperties.load(isDb);
            storage = new DbConfig(
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

    public DbConfig getStorage() {
        return storage;
    }
}
