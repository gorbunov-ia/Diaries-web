package ru.gorbunov.diaries.service.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.repository.UserRepository;
import ru.gorbunov.diaries.security.SecurityUtils;

import java.util.Optional;

/**
 * Implementation of service for interaction with user.
 *
 * @author Gorbunov.ia
 */
@Service
@Transactional
public class UserInternalServiceImpl implements UserInternalService {

    /**
     * Logger for class.
     */
    private final Logger log = LoggerFactory.getLogger(UserInternalServiceImpl.class);

    /**
     * Repository for Users.
     */
    private final UserRepository userRepository;

    /**
     * Base constructor.
     *
     * @param repository repository for crud operation with db
     */
    public UserInternalServiceImpl(final UserRepository repository) {
        this.userRepository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByLogin(final String login) {
        return userRepository.findOneByLogin(login);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Optional<User> getUser(final Integer id) {
        return userRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Optional<User> getUser() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
    }

}
