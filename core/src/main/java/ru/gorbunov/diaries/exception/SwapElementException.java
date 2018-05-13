package ru.gorbunov.diaries.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for swap of note elements.
 *
 * @author Gorbunov.ia
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SwapElementException extends RuntimeException {
}
