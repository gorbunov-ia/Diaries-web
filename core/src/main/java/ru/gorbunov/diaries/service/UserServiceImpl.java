package ru.gorbunov.diaries.service;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.gorbunov.diaries.controller.dto.UserDto;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.service.internal.UserInternalService;

import java.util.Optional;

/**
 * Implementation of service for interaction with user.
 *
 * @author Gorbunov.ia
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    /**
     * Internal service for interaction with user.
     */
    private final UserInternalService userInternalService;

    /**
     * A service interface for type conversion.
     */
    private final ConversionService conversionService;

    /**
     * Base constructor.
     *
     * @param userInternalService internal service for interaction with user.
     * @param conversionService   spring conversion service
     */
    UserServiceImpl(UserInternalService userInternalService, ConversionService conversionService) {
        this.userInternalService = userInternalService;
        this.conversionService = conversionService;

    }

    @Override
    public Optional<UserDto> getUser() {
        Optional<User> user = userInternalService.getUser();
        return user.map(usr -> conversionService.convert(usr, UserDto.class));
    }

}
