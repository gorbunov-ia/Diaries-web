package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.domain.User;

/**
 * Service for interaction with user.
 *
 * @author Gorbunov.ia
 */
public interface UserService {

    /**
     * Method get user by login.
     *
     * @param login user login in db
     * @return      user object
     */
    User getUserByLogin(String login);

    /**
     * Method get user by id.
     *
     * @param id    user id in db
     * @return      user object
     */
    User getUser(Integer id);

    /**
     * Method get current authenticated user.
     *
     * @return user object
     */
    User getUser();

}
