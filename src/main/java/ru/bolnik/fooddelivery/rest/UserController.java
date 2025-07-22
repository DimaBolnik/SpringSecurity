package ru.bolnik.fooddelivery.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bolnik.fooddelivery.dto.UserDto;
import ru.bolnik.fooddelivery.dto.converters.UserDtoConverter;
import ru.bolnik.fooddelivery.model.User;
import ru.bolnik.fooddelivery.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDtoConverter userDtoConverter;
    private final UserService userService;

    @GetMapping("/user")
    public List<UserDto> getAll() {
        return userService.getAll()
                .stream()
                .map(userDtoConverter::toDto)
                .toList();
    }

    @GetMapping("/user/{id}")
    public UserDto getById(@PathVariable("id") long userId) {
        User user = userService.getById(userId);
        return userDtoConverter.toDto(user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    public UserDto create(@RequestBody UserDto userDto) {
        var user = userDtoConverter.toDomain(userDto);
        User userSave = userService.create(user);
        return userDtoConverter.toDto(userSave);
    }

    @PutMapping("/user")
    public UserDto update(@RequestBody UserDto userDto) {
        var user = userDtoConverter.toDomain(userDto);
        User userUpdate = userService.update(user);
        return userDtoConverter.toDto(userUpdate);
    }
}
