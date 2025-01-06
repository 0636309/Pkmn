package ru.mirea.pkmn.kryukovakn.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.pkmn.kryukovakn.entity.UserEntity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUserByUsername(String username);
}
