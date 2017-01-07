package org.zelenikr.pia.validation;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Roman Zelenik
 */
public class ConfigurablePayeeAccountValidation implements PayeeAccountValidation {

    @Value("${validation.payeeAccount.number.maxLength}")
    private int numberMaxLength;

    @Value("${validation.payeeAccount.number.minLength}")
    private int numberMinLength;

    @Value("${validation.payeeAccount.bankCodeLength}")
    private int bankCodeLength;

    @Override
    public int getNumberMaxLength() {
        return numberMaxLength;
    }

    @Override
    public int getNumberMinLength() {
        return numberMinLength;
    }

    @Override
    public int getBankCodeLength() {
        return bankCodeLength;
    }
}
