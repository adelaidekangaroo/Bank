package org.sberbank.simonov.bank.util;

import java.io.File;
import java.util.Objects;

public abstract class AbstractConfig {
    protected static File getPropsFile(String filePath) {
        String path = Config.class.getClassLoader().getResource(filePath).getPath();
        Objects.requireNonNull(path);
        return new File(path);
    }
}
