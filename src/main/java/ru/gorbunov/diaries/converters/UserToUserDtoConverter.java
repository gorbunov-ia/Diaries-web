package ru.gorbunov.diaries.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.gorbunov.diaries.controller.vm.UserDto;
import ru.gorbunov.diaries.domain.User;

/**
 * Converter User class to UserDto class.
 *
 * @author Gorbunov.ia
 */
@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        if (source == null) {
            throw new IllegalArgumentException("The source argument must to be NOT null.");
        }
        UserDto target = new UserDto();
        target.setId(source.getId());
        target.setLogin(source.getLogin());
        target.setEmail(source.getEmail());
        target.setActive(source.getIsActive());
        return target;
    }

}
