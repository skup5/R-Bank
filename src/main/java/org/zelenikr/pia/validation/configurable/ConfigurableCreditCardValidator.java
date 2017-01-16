package org.zelenikr.pia.validation.configurable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.CreditCard;
import org.zelenikr.pia.validation.CreditCardValidator;
import org.zelenikr.pia.validation.exception.CreditCardValidationException;

/**
 * @author Roman Zelenik
 */
@Service
public class ConfigurableCreditCardValidator implements CreditCardValidator {

    @Value("${validation.creditCard.number.length}")
    private int creditCardNumberLength;

    @Override
    public int getCreditCardNumberLength() {
        return creditCardNumberLength;
    }

    @Override
    public void validate(CreditCard creditCard) throws CreditCardValidationException {
        validateCreditCardNumber(creditCard.getCreditCardNumber());
    }

    private void validateCreditCardNumber(String creditCardNumber) throws CreditCardValidationException {
        if (StringUtils.isBlank(creditCardNumber))
            throw new CreditCardValidationException("Credit card number is a required field");
        if (creditCardNumber.length() != getCreditCardNumberLength())
            throw new CreditCardValidationException("Credit card number must be " + getCreditCardNumberLength() + " digits");
        if (!StringUtils.isNumeric(creditCardNumber))
            throw new CreditCardValidationException("Credit card number must be a positive numeric value");
    }

}
