package org.sberbank.simonov.bank;

import com.sun.net.httpserver.HttpServer;
import org.sberbank.simonov.bank.repository.jdbc.util.DbConfig;
import org.sberbank.simonov.bank.web.Dispatcher;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {

    public static final int PORT = 8080;
    public static HttpServer server;
    public static Dispatcher dispatcher;

    public static void main(String[] args) throws IOException {
        DbConfig.initDb();

        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.setExecutor(null); // creates a default executor
        server.start();

        dispatcher = new Dispatcher(server);
    }
}