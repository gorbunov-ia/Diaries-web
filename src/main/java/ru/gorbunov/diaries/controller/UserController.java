package ru.gorbunov.diaries.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.repository.UserRepository;

/**
 * Controller for user page.
 *
 * @author Gorbunov.ia
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {

    /**
     * Repository for Users.
     */
    private final UserRepository userRepository;

    /**
     * Base constructor.
     *
     * @param userRepository repository for crud operation with db
     */
    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
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
        User user = userRepository.findOneByLogin("test");
        //todo: HttpStatus.NOT_FOUND
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
