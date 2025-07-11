package ru.bolnik.fooddelivery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bolnik.fooddelivery.entities.Role;
import ru.bolnik.fooddelivery.entities.User;
import ru.bolnik.fooddelivery.services.UserService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DefaultDataConfig {

    @Autowired
    private UserService userService;

    @PostConstruct
    private void createDefaultUsers() {
        List.of(
                User.builder().username("john").password("123").build(),
                User.builder().username("jane").password("123").build()
        ).forEach(userService::createUser);

        userService.createUser(
                User.builder().username("admin").password("admin").roles(new HashSet<>(Arrays.asList(Role.ROLE_ADMIN))).build()
        );

        userService.createUser(
                User.builder().username("moder").password("moder").roles(new HashSet<>(Arrays.asList(Role.ROLE_MODERATOR))).build()
        );

        userService.createUser(
                User.builder().username("umoderator").password("12345").roles(new HashSet<>(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_MODERATOR))).build()
        );
    }

}