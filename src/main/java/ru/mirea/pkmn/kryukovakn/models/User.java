package ru.mirea.pkmn.kryukovakn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.pkmn.kryukovakn.entity.UserEntity;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;

    private String role;

    private List<User> followers;

    public static User fromEntity(UserEntity entity) {
        return User.builder()
                .username(entity.getUsername())
                .role(entity.getRole())
                .followers(entity.getFollowers().stream().map(User::fromEntity).toList())
                .build();
    }

}
