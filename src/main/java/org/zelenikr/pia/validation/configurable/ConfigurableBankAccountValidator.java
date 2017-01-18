package org.zelenikr.pia.validation.configurable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Currency;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
import org.zelenikr.pia.validation.BankAccountValidator;

/**
 * @author Roman Zelenik
 */
@Service
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
        validateCurrency(bankAccount.getCurrency());
    }

    private void validateAccountNumber(String accountNumber) throws BankAccountValidationException {
        if (StringUtils.isBlank(accountNumber)) throw new BankAccountValidationException("Account number is a required field");
        if (accountNumber.length() != getBankAccountNumberLength())
            throw new BankAccountValidationException("Account number must be " + getBankAccountNumberLength() + " digits");
        if (!StringUtils.isNumeric(accountNumber)) throw new BankAccountValidationException("Account number must be a positive numeric value");
    }

    private void validateCurrency(Currency currency) throws BankAccountValidationException {
        if(currency == null) throw new BankAccountValidationException("Currency is required field.");
    }
}
