package org.sberbank.simonov.bank.web;

import com.sun.net.httpserver.HttpServer;
import org.sberbank.simonov.bank.config.DbConfig;
import org.sberbank.simonov.bank.config.WebConfig;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;

public class Server {
    private static HttpServer server;

    public static void startServer() throws IOException {
        DbConfig.get().getConnect().initDb();
        try {
            server = HttpServer.create(
                    new InetSocketAddress(WebConfig.get().SERVER_PORT),
                    WebConfig.get().SERVER_BACKLOG
            );
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (BindException e) {
            e.printStackTrace();
        }
        new Dispatcher(server);
    }

    public static void stopServer() throws IOException {
        if (server != null) {
            server.stop(0);
        }
    }
}

