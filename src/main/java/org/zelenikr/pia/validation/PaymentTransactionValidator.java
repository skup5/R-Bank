package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;
import org.zelenikr.pia.validation.exception.PaymentTransactionValidationException;

/**
 * @author Roman Zelenik
 */
public interface PaymentTransactionValidator extends DomainValidator<PaymentTransaction> {

    /**
     * Validates that payment transaction instance is currently in a valid state.
     *
     * @throws PaymentTransactionValidationException in case the payment transaction is not in valid state.
     * @throws OffsetAccountValidationException       in case the payee account in transaction is not in valid state.
     */
    @Override
    void validate(PaymentTransaction transaction) throws PaymentTransactionValidationException, OffsetAccountValidationException;
}
