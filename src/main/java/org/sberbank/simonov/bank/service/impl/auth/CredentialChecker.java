package org.sberbank.simonov.bank.service.impl.auth;

@FunctionalInterface
public interface CredentialChecker {
    boolean check(String login, String password);
}
