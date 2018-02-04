package ru.gorbunov.diaries.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.service.UserService;

/**
 * Controller for user page.
 *
 * @author Gorbunov.ia
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {

    /**
     * Service for interaction with user.
     */
    private final UserService userService;

    /**
     * Base constructor.
     *
     * @param userService service for interaction with user
     */
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Method to get current user info.
     *
     * @return template name
     */
    @GetMapping(path = "")
    public ResponseEntity<User> getCurrentUser() {
        //todo: logs
        //todo: real current user request
        User user = userService.getUserByLogin("test");
        //todo: HttpStatus.NOT_FOUND
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
