package org.sberbank.simonov.bank.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebConfig extends AbstractConfig {

    private static final File WEB_PROPS = getPropsFile("web/server.properties");
    private static final WebConfig INSTANCE = new WebConfig();

    public final int SERVER_PORT;
    public final int SERVER_BACKLOG;
    public final String SERVER_USER_CONTEXT;
    public final String SERVER_ADMIN_CONTEXT;

    public static WebConfig get() {
        return INSTANCE;
    }

    private WebConfig() {
        try (InputStream isWeb = new FileInputStream(WEB_PROPS)) {
            Properties webProperties = new Properties();
            webProperties.load(isWeb);
            SERVER_PORT = Integer.parseInt(webProperties.getProperty("server.port"));
            SERVER_BACKLOG = Integer.parseInt(webProperties.getProperty("server.backlog"));
            SERVER_USER_CONTEXT = webProperties.getProperty("server.userContext");
            SERVER_ADMIN_CONTEXT = webProperties.getProperty("server.adminContext");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + WEB_PROPS.getPath());
        }
    }
}
