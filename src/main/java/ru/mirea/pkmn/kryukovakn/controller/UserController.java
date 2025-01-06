package ru.mirea.pkmn.kryukovakn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.pkmn.kryukovakn.models.User;
import ru.mirea.pkmn.kryukovakn.services.UserService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public User getUser(@RequestParam String name) throws UserPrincipalNotFoundException {
        return userService.getUserByName(name);
    }

    @GetMapping("/followers")
    public List<User> getUserFollowers(@RequestBody User user) throws UserPrincipalNotFoundException {
        return userService.getUserFollowers(user);
    }
}
