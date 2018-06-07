package ru.gorbunov.diaries.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for bad request case.
 *
 * @author Gorbunov.ia
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    /**
     * Create exception with message.
     *
     * @param message description
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Create exception with message of user is absent.
     *
     * @return exception instance
     */
    public static BadRequestException ofUser() {
        return new BadRequestException("User not found.");
    }

    /**
     * Create exception with message of save request with id.
     *
     * @return exception instance
     */
    public static BadRequestException ofPresentId() {
        return new BadRequestException("Request should not include id field.");
    }

    /**
     * Create exception with message of save request without id.
     *
     * @return exception instance
     */
    public static BadRequestException ofAbsentId() {
        return new BadRequestException("Request should include id field");
    }
}
