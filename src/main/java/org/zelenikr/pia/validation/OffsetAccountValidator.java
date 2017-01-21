package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.OffsetAccount;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;

/**
 * @author Roman Zelenik
 */
public interface OffsetAccountValidator {
    int getNumberMaxLength();

    int getNumberMinLength();

    int getBankCodeLength();

    /**
     * Validates that payee account instance is currently in a valid state.
     *
     * @throws OffsetAccountValidationException in case the payee account is not in valid state.
     */
    void validate(OffsetAccount account) throws OffsetAccountValidationException;
}
