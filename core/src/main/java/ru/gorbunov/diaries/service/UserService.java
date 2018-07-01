package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.controller.dto.UserDto;

import java.util.Optional;

/**
 * Internal service for interaction with user.
 *
 * @author Gorbunov.ia
 */
public interface UserService {

    /**
     * Method get current authenticated user.
     *
     * @return user dto object
     */
    Optional<UserDto> getUser();

}
