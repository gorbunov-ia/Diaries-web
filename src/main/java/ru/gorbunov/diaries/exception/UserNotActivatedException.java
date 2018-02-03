package ru.gorbunov.diaries.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception for user without activation.
 *
 * @author Gorbunov.ia
 */
public class UserNotActivatedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    /**
     * Light constructor.
     *
     * @param message exception message
     */
    public UserNotActivatedException(final String message) {
        super(message);
    }

    /**
     * Base constructor.
     *
     * @param message   exception message
     * @param t         throwable
     */
    public UserNotActivatedException(final String message, final Throwable t) {
        super(message, t);
    }
}

