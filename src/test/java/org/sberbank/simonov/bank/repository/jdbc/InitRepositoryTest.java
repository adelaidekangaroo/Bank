package org.sberbank.simonov.bank.repository.jdbc;

import org.junit.Before;
import org.sberbank.simonov.bank.repository.jdbc.util.DbConfig;

public abstract class InitRepositoryTest {
    @Before
    public void resetDb() {
        DbConfig.initDb();
    }
}
