package ru.mirea.pkmn.kryukovakn.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mirea.pkmn.kryukovakn.entity.UserEntity;
import ru.mirea.pkmn.kryukovakn.repository.UserRepository;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final UserRepository userRepository;

    @SneakyThrows
    public UserEntity getUserById(Integer id)  {
        return userRepository.findById(id).orElseThrow(
                () -> new UserPrincipalNotFoundException("User with id " + id + " not found")
        );
    }

    @SneakyThrows
    public UserEntity getUserByName(String name) {
        return userRepository.findUserByUsername(name).orElseThrow(
                () -> new UserPrincipalNotFoundException("User with name " + name + " not found")
        );
    }
}
