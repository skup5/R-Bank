package org.zelenikr.pia.domain.exception;

import org.zelenikr.pia.validation.ValidationException;

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
