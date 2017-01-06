package org.zelenikr.pia.domain.exception;

import org.zelenikr.pia.validation.ValidationException;

/**
 * Exception thrown when User object fails internal state validation.
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class UserValidationException extends ValidationException {

    public UserValidationException(String message) {
        super(message);
    }

}
