package org.sberbank.simonov.bank;

import org.junit.Before;
import org.sberbank.simonov.bank.util.Config;

public abstract class ResetDbTest {
    @Before
    public void resetDb() {
        Config.get().getStorage().initDb();
    }
}
