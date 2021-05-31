package org.sberbank.simonov.bank;

import org.junit.Before;
import org.sberbank.simonov.bank.config.DbConfig;

public abstract class ResetDbTest {
    @Before
    public void resetDb() {
        DbConfig.get().getConnect().initDb();
    }
}
