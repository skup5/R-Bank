package org.zelenikr.pia.validation.exception;

/**
 * Exception thrown when Client object fails internal state validation.
 *
 * @author Roman Zelenik
 */
public class ClientValidationException extends ValidationException {

    public ClientValidationException(String message) {
        super(message);
    }
}
