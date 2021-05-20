package org.sberbank.simonov.bank.service.auth;

public interface CredentialChecker {
    boolean check(String login, String password);
}
