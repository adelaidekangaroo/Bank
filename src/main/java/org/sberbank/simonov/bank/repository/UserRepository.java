package org.sberbank.simonov.bank.repository;

import org.sberbank.simonov.bank.model.User;

import java.util.List;

public interface UserRepository {

    boolean save(User user);

    List<User> getAllCounterparties(int currentUserId);

    User getById(int id);
}
