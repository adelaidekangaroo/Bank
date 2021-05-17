package org.sberbank.simonov.bank.repository.jdbc;

import org.junit.Before;

import static org.sberbank.simonov.bank.repository.jdbc.Queries.INIT_DB;
import static org.sberbank.simonov.bank.repository.jdbc.Queries.POPULATE;

public abstract class RepositoryTest {
    @Before
    public void resetDb() {
        DbConfig.executeScript(INIT_DB);
        DbConfig.executeScript(POPULATE);
    }
}
