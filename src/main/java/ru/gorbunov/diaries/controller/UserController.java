package ru.gorbunov.diaries.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.repository.UserRepository;

/**
 *
 * @author Gorbunov.ia
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {
    
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "")
    public ResponseEntity<User> getCurrentUser () {
        //TODO: logs
        User user = userRepository.findOneByLogin("test");
                
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
}
