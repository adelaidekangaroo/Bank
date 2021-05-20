package org.sberbank.simonov.bank.service.auth;

import com.sun.net.httpserver.Authenticator;
import org.sberbank.simonov.bank.model.Role;

public interface AuthUserService {
    Authenticator getAuthByRole(Role role);
}
