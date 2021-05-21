package org.sberbank.simonov.bank.service.impl;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import org.sberbank.simonov.bank.exception.StorageException;
import org.sberbank.simonov.bank.model.Role;
import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.repository.UserRepository;
import org.sberbank.simonov.bank.repository.jdbc.UserRepositoryImpl;
import org.sberbank.simonov.bank.service.UserService;
import org.sberbank.simonov.bank.service.impl.auth.AuthUserService;
import org.sberbank.simonov.bank.service.impl.auth.CredentialChecker;
import org.sberbank.simonov.bank.to.UserTo;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static org.sberbank.simonov.bank.util.ValidationUtil.*;

public class UserServiceImpl implements UserService, AuthUserService {

    private final UserRepository repository = new UserRepositoryImpl();

    @Override
    public Authenticator getAuthByRole(Role role) {
        switch (role) {
            case USER:
                return getAuth((login, password) -> {
                    User user = repository.getByLogin(login);
                    if (user == null) return false;
                    else return user.getPassword().equals(password);
                });
            case EMPLOYEE:
                return getAuth((login, password) -> {
                    User user = repository.getByLogin(login);
                    if (user == null) return false;
                    else return user.getPassword().equals(password) && user.getRole() == Role.EMPLOYEE;
                });
            default:
                throw new IllegalArgumentException();
        }
    }

    private Authenticator getAuth(CredentialChecker checker) {
        return new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String login, String password) {
                try {
                    return checker.check(login, password);
                } catch (StorageException e) {
                    return false;
                }
            }
        };
    }

    @Override
    public void create(User user) {
        requireNonNull(user);
        checkNew(user);
        checkSave(repository.create(user), User.class);
    }

    @Override
    public List<UserTo> getAllCounterparties(int currentUserId) {
        return repository.getAllCounterparties(currentUserId)
                .stream()
                .map(UserTo::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserTo getById(int id) {
        User user = repository.getById(id);
        return new UserTo(checkNotFoundWithId(user, id));
    }
}
