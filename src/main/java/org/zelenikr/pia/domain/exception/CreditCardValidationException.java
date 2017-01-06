package org.zelenikr.pia.domain.exception;

import org.zelenikr.pia.validation.ValidationException;

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
