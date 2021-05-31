package org.sberbank.simonov.bank.service.impl.auth;

import org.sberbank.simonov.bank.model.User;

@FunctionalInterface
public interface CredentialChecker {
    boolean check(User user, String login, String password);
}
