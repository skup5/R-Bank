package org.zelenikr.pia.validation.exception;

/**
 * Exception thrown when CreditCard object fails internal state validation.
 *
 * @author Roman Zelenik
 */
public class CreditCardValidationException extends ValidationException {

    public CreditCardValidationException(String message) {
        super(message);
    }
}
