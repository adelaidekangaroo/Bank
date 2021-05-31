package org.sberbank.simonov.bank.util;

import org.sberbank.simonov.bank.repository.jdbc.util.DbConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class Config {

    private static final File PROPS = getPropsFile();
    private static final Config INSTANCE = new Config();

    private final DbConfig storage;

    public static Config get() {
        return INSTANCE;
    }

    private static File getPropsFile() {
        String path = Config.class.getClassLoader().getResource("db/h2.properties").getPath();
        Objects.requireNonNull(path);
        return new File(path);
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(is);
            storage = new DbConfig(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"),
                    properties.getProperty("db.driver"),
                    properties.getProperty("db.init"),
                    properties.getProperty("db.populate")

            );
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public DbConfig getStorage() {
        return storage;
    }
}
