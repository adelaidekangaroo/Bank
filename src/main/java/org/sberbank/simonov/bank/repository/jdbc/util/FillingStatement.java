package org.sberbank.simonov.bank.repository.jdbc.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface FillingStatement {
    void state(PreparedStatement statement) throws SQLException;
}
