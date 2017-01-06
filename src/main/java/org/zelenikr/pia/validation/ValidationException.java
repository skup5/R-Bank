package org.zelenikr.pia.validation;

/**
 * Exception thrown when object fails internal state validation.
 *
 * @author Roman Zelenik
 */
public abstract class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
