package org.zelenikr.pia.validation.configurable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
import org.zelenikr.pia.validation.BankAccountValidator;

/**
 * @author Roman Zelenik
 */
public class ConfigurableBankAccountValidator implements BankAccountValidator {

    @Value("${validation.bankAccount.number.length}")
    private int bankAccountNumberLength;

    @Override
    public int getBankAccountNumberLength() {
        return bankAccountNumberLength;
    }

    @Override
    public void validate(BankAccount bankAccount) throws BankAccountValidationException {
        validateAccountNumber(bankAccount.getAccountNumber());
    }

    private void validateAccountNumber(String accountNumber) throws BankAccountValidationException {
        if (StringUtils.isBlank(accountNumber)) throw new BankAccountValidationException("Account number is a required field");
        if (accountNumber.length() != getBankAccountNumberLength())
            throw new BankAccountValidationException("Account number must be " + getBankAccountNumberLength() + " digits");
        if (!StringUtils.isNumeric(accountNumber)) throw new BankAccountValidationException("Account number must be a positive numeric value");
    }

}
