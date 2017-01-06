package org.zelenikr.pia.validation;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Roman Zelenik
 */
public class ConfigurableCreditCardValidation implements CreditCardValidation {

    @Value("${validation.creditCard.number.length}")
    private int creditCardNumberLength;

    @Override
    public int getCreditCardNumberLength() {
        return creditCardNumberLength;
    }
}
