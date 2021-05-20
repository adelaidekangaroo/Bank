package org.sberbank.simonov.bank.service;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.repository.UserRepository;
import org.sberbank.simonov.bank.repository.jdbc.UserRepositoryImpl;

public class AuthHelper {

    private final UserRepository repository = new UserRepositoryImpl();

    public Authenticator getUserAuth() {
        return new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String login, String password) {
                User user = repository.getByLogin(login);
                if (user == null) return false;
                else return user.getPassword().equals(password);
            }
        };
    }

    public Authenticator getAdminAuth() {
        return new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String login, String password) {
                User user = repository.getByLogin(login);
                if (user == null) return false;
                else return user.getPassword().equals(password) && user.getRole() == Role.EMPLOYEE;
            }
        };
    }
}
