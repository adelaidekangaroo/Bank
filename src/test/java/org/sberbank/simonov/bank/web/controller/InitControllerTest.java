package org.sberbank.simonov.bank.web.controller;

import com.google.gson.Gson;
import org.junit.BeforeClass;
import org.sberbank.simonov.bank.App;
import org.sberbank.simonov.bank.ResetDbTest;

import java.io.IOException;
import java.util.Base64;

public abstract class InitControllerTest extends ResetDbTest {

    protected static final Gson GSON = new Gson();

    @BeforeClass
    public static void beforeTesting() throws IOException {
        App.main(new String[0]);
    }

    public static String encode(String logPass) {
        return new String(Base64.getEncoder().encode(logPass.getBytes()));
    }
}
