package org.zelenikr.pia.domain.exception;

import org.zelenikr.pia.validation.exception.ValidationException;

/**
 * Exception thrown when Person object fails internal state validation.
 *
 * @author Roman Zelenik
 */
public class PersonValidationException extends ValidationException {

    public PersonValidationException(String message) {
        super(message);
    }
}
