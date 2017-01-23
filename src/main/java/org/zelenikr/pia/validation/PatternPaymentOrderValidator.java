package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.PatternPaymentOrder;
import org.zelenikr.pia.validation.exception.PatternPaymentOrderValidationException;

/**
 * @author Roman Zelenik
 */
public interface PatternPaymentOrderValidator extends DomainValidator<PatternPaymentOrder> {

    int getNameMaxLength();

    int getNameMinLength();

    /**
     * Validates that {@link PatternPaymentOrder} instance is currently in a valid state.
     *
     * @throws PatternPaymentOrderValidationException in case the pattern of payment order is not in valid state.
     */
    @Override
    void validate(PatternPaymentOrder pattern) throws PatternPaymentOrderValidationException;
}
