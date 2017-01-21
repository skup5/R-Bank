package org.zelenikr.pia.validation.exception;


/**
 * Exception thrown when OffsetAccount object fails internal state validation.
 *
 * @author Roman Zelenik
 */
public class OffsetAccountValidationException extends ValidationException {

    public OffsetAccountValidationException(String message) {
        super(message);
    }
}
