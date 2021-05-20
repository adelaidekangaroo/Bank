package org.sberbank.simonov.bank.service;

import org.sberbank.simonov.bank.model.User;
import org.sberbank.simonov.bank.to.UserTo;

import java.util.List;

public interface UserService {

    void create(User user);

    List<UserTo> getAllCounterparties(int currentUserId);

    UserTo getById(int id);
}
