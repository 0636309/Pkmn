package ru.mirea.pkmn.kryukovakn.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.pkmn.kryukovakn.dao.UserDao;
import ru.mirea.pkmn.kryukovakn.entity.UserEntity;
import ru.mirea.pkmn.kryukovakn.models.User;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public List<User> getUserFollowers(User user) throws UserPrincipalNotFoundException {
        UserEntity userEntity = userDao.getUserByName(user.getUsername());
        List<UserEntity> followers = userEntity.getFollowers();
        if(followers.isEmpty()){
            throw new RuntimeException();
        }

        return followers.stream().map(User::fromEntity).toList();
    }

    @Override
    public User getUserByName(String name) throws UserPrincipalNotFoundException {
        UserEntity userEntity = userDao.getUserByName(name);

        return User.fromEntity(userEntity);
    }

    @Override
    public User getUserById(Integer id) throws UserPrincipalNotFoundException {
        UserEntity userEntity = userDao.getUserById(id);
        return User.fromEntity(userEntity);
    }

    @Override
    public User createUser(User user) {
        // Пока не реализовано
        return null;
    }
}
