package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.domain.User;

import java.util.Optional;

/**
 * Service for interaction with user.
 *
 * @author Gorbunov.ia
 */
public interface UserInternalService {

    /**
     * Method get user by login.
     *
     * @param login user login in db
     * @return      user object
     */
    Optional<User> getUserByLogin(String login);

    /**
     * Method get user by id.
     *
     * @param id    user id in db
     * @return      user object
     */
    Optional<User> getUser(Integer id);

    /**
     * Method get current authenticated user.
     *
     * @return user object
     */
    Optional<User> getUser();

}
