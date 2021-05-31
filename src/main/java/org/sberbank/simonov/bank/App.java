package org.sberbank.simonov.bank;

import com.sun.net.httpserver.HttpServer;
import org.sberbank.simonov.bank.util.config.DbConfig;
import org.sberbank.simonov.bank.util.config.WebConfig;
import org.sberbank.simonov.bank.web.Dispatcher;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;

public class App {

    public static HttpServer server;
    public static Dispatcher dispatcher;

    public static void main(String[] args) throws IOException {
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

        dispatcher = new Dispatcher(server);
    }
}