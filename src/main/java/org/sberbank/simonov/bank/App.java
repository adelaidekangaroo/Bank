package org.sberbank.simonov.bank;

import org.sberbank.simonov.bank.web.Server;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Server.startServer();
    }
}