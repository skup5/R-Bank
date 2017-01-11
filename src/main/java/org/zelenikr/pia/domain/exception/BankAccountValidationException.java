package org.zelenikr.pia.domain.exception;

import org.zelenikr.pia.validation.exception.ValidationException;

/**
 * Exception thrown when BankAccount object fails internal state validation.
 *
 * @author Roman Zelenik
 */
public class BankAccountValidationException extends ValidationException {

    public BankAccountValidationException(String message) {
        super(message);
    }
}
