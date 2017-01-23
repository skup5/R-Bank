package org.zelenikr.pia.validation.exception;

import org.zelenikr.pia.validation.exception.ValidationException;

/**
 * Exception thrown when PatternPaymentOrder object fails internal state validation.
 *
 * @author Roman Zelenik
 */
public class PatternPaymentOrderValidationException extends ValidationException {

    public PatternPaymentOrderValidationException(String message) {
        super(message);
    }
}
