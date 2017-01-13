package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;

/**
 * @author Roman Zelenik
 */
public interface BankAccountValidator extends DomainValidator<BankAccount> {
    int getBankAccountNumberLength();

    /**
     * Validates that bank account instance is currently in a valid state.
     *
     * @throws BankAccountValidationException in case the bank account is not in valid state.
     */
    @Override
    void validate(BankAccount domainObject) throws BankAccountValidationException;
}
