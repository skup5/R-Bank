package org.zelenikr.pia.validation;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Roman Zelenik
 */
public class ConfigurableBankAccountValidation implements BankAccountValidation {

    @Value("${validation.bankAccount.number.length}")
    private int bankAccountNumberLength;

    @Override
    public int getBankAccountNumberLength() {
        return bankAccountNumberLength;
    }
}
