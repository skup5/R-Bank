package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.CreditCard;
import org.zelenikr.pia.validation.exception.CreditCardValidationException;

/**
 * @author Roman Zelenik
 */
public interface CreditCardValidator extends DomainValidator<CreditCard> {
    int getCreditCardNumberLength();

    /**
     * Validates that credit card instance is currently in a valid state.
     *
     * @throws CreditCardValidationException in case the credit card is not in valid state.
     */
    @Override
    void validate(CreditCard creditCard) throws CreditCardValidationException;
}
