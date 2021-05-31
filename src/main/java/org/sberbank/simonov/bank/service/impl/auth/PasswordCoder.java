package org.sberbank.simonov.bank.service.impl.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordCoder {

    private PasswordCoder() {
    }

    public static boolean equalsPasswords(String psw1, String psw2) {
        return decode(psw1).equals(decode(psw2));
    }

    public static String decode(String s) {
        return new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)));
    }

    public static String encode(String s) {
        return new String(Base64.getEncoder().encode(s.getBytes(StandardCharsets.UTF_8)));
    }
}
