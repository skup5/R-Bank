package org.zelenikr.pia.validation.exception;

/**
 * Exception thrown when User object fails internal state validation.
 * <p>
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class UserValidationException extends ValidationException {

    public UserValidationException(String message) {
        super(message);
    }

}
