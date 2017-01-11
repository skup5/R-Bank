package org.zelenikr.pia.domain.exception;

import org.zelenikr.pia.validation.exception.ValidationException;

/**
 * Exception thrown when PayeeAccount object fails internal state validation.
 * @author Roman Zelenik
 */
public class PayeeAccountValidationException extends ValidationException {

    public PayeeAccountValidationException(String message) {
        super(message);
    }
}
