package org.zelenikr.pia.domain.exception;

import org.zelenikr.pia.validation.ValidationException;

/**
 * Exception thrown when SinglePaymentOrder object fails internal state validation.
 *
 * @author Roman Zelenik
 */
public class SinglePaymentOrderValidationException extends ValidationException {

    public SinglePaymentOrderValidationException(String message) {
        super(message);
    }
}
