package org.sberbank.simonov.bank.repository.jdbc.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface FillingStatement {
    void state(PreparedStatement statement) throws SQLException;
}
