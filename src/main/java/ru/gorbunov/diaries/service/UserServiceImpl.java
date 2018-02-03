package ru.gorbunov.diaries.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.repository.UserRepository;
import ru.gorbunov.diaries.security.SecurityUtils;

/**
 * Implementation of service for interaction with user.
 *
 * @author Gorbunov.ia
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * Logger for class.
     */
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Repository for Users.
     */
    private final UserRepository userRepository;

    /**
     * Base constructor.
     * @param repository repository for crud operation with db
     */
    public UserServiceImpl(final UserRepository repository) {
        this.userRepository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public User getUserByLogin(final String login) {
        return userRepository.findOneByLogin(login);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public User getUser(final Integer id) {
        return userRepository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public User getUser() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
    }

}
