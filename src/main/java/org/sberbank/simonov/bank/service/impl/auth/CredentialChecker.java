package org.sberbank.simonov.bank.service.impl.auth;

public interface CredentialChecker {
    boolean check(String login, String password);
}
