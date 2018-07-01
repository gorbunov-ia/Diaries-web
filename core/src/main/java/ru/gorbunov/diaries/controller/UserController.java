package ru.gorbunov.diaries.controller;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.gorbunov.diaries.controller.dto.UserDto;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.security.SecurityUtils;
import ru.gorbunov.diaries.service.UserInternalService;

import java.util.Optional;

/**
 * Controller for user page.
 *
 * @author Gorbunov.ia
 */
@RestController
@RequestMapping(path = "api/user")
public class UserController {

    /**
     * Service for interaction with user.
     */
    private final UserInternalService userInternalService;

    /**
     * A service interface for type conversion.
     */
    private final ConversionService conversionService;

    /**
     * Base constructor.
     *
     * @param userInternalService       service for interaction with user
     * @param conversionService Spring conversion service
     */
    public UserController(final UserInternalService userInternalService, final ConversionService conversionService) {
        this.userInternalService = userInternalService;
        this.conversionService = conversionService;
    }

    /**
     * Method to get current user info.
     *
     * @return template name
     */
    @GetMapping(path = "")
    public ResponseEntity<UserDto> getCurrentUser() {
        Optional<User> user = userInternalService.getUserByLogin(SecurityUtils.getCurrentUserLogin());
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto result = conversionService.convert(user.get(), UserDto.class);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
