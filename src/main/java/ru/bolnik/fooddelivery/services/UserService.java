package ru.bolnik.fooddelivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolnik.fooddelivery.entities.Role;
import ru.bolnik.fooddelivery.entities.User;
import ru.bolnik.fooddelivery.repositories.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void createUser(User user) {

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>(Arrays.asList(Role.USER)));
        }

        userRepository.save(user);
    }

    @Transactional
    public User get(Long id) {
        return userRepository.findById(id).get();
    }


    @Transactional
    public List<User> getList() {
        return userRepository.findAll();
    }
}
