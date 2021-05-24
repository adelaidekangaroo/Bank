package org.sberbank.simonov.bank.web.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.sberbank.simonov.bank.App;
import org.sberbank.simonov.bank.repository.jdbc.util.DbConfig;

import java.io.IOException;

public abstract class InitControllerTest {
    @BeforeClass
    public static void beforeTesting() throws IOException {
        //App.main(new String[0]);
    }

    @Before
    public void resetDb() {
        DbConfig.initDb();
    }
}
