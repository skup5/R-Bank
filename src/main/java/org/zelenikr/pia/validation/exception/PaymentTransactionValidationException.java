package org.zelenikr.pia.validation.exception;

/**
 * Exception thrown when PaymentTransaction object fails internal state validation.
 *
 * @author Roman Zelenik
 */
public class PaymentTransactionValidationException extends ValidationException {

    public PaymentTransactionValidationException(String message) {
        super(message);
    }
}
