package org.sberbank.simonov.bank.web.controller;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.sberbank.simonov.bank.ResetDbTest;
import org.sberbank.simonov.bank.web.Server;

import java.io.IOException;

public abstract class InitControllerTest extends ResetDbTest {

    protected static final Gson GSON = new Gson();

    @BeforeClass
    public static void runServer() throws IOException {
        Server.startServer();
    }

    @AfterClass
    public static void shutdownServer() throws IOException {
        Server.stopServer();
    }
}
