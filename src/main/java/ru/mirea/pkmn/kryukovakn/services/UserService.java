package ru.mirea.pkmn.kryukovakn.services;

import ru.mirea.pkmn.kryukovakn.models.User;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

public interface UserService {

    List<User> getUserFollowers(User user) throws UserPrincipalNotFoundException;

    User getUserByName(String name) throws UserPrincipalNotFoundException;

    User getUserById(Integer id) throws UserPrincipalNotFoundException;

    User createUser(User user);
}
