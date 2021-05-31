package org.sberbank.simonov.bank.service.impl.auth;

import com.sun.net.httpserver.Authenticator;
import org.sberbank.simonov.bank.model.Role;

@FunctionalInterface
public interface AuthService {
    Authenticator getAuthByRole(Role role);
}
