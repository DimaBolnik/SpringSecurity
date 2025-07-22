package ru.bolnik.fooddelivery.dto.converters;


import org.springframework.stereotype.Component;
import ru.bolnik.fooddelivery.dto.UserDto;
import ru.bolnik.fooddelivery.model.User;

@Component
public class UserDtoConverter {

    public UserDto toDto(User domain) {
        return new UserDto(domain.getId(), domain.getLogin(), null, domain.getRoles());
    }

    public User toDomain(UserDto dto) {
        var user = new User();
        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles());
        return user;
    }
}
