package org.sberbank.simonov.bank;

import com.sun.net.httpserver.HttpServer;
import org.sberbank.simonov.bank.util.Config;
import org.sberbank.simonov.bank.web.Dispatcher;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;

public class App {

    public static final int PORT = 8080;
    public static HttpServer server;
    public static Dispatcher dispatcher;

    public static void main(String[] args) throws IOException {
        Config.get().getStorage().initDb();

        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (BindException e) {
            e.printStackTrace();
        }

        dispatcher = new Dispatcher(server);
    }
}