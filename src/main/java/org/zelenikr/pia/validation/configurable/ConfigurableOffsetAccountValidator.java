package org.zelenikr.pia.validation.configurable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.OffsetAccount;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;
import org.zelenikr.pia.validation.OffsetAccountValidator;

/**
 * @author Roman Zelenik
 */
@Service
public class ConfigurableOffsetAccountValidator implements OffsetAccountValidator {

    @Value("${validation.offsetAccount.number.maxLength}")
    private int numberMaxLength;

    @Value("${validation.offsetAccount.number.minLength}")
    private int numberMinLength;

    @Value("${validation.offsetAccount.bankCode.length}")
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

    @Override
    public void validate(OffsetAccount account) throws OffsetAccountValidationException {
        validateOffsetAccountNumber(account.getOffsetAccountNumber());
        validateBankCode(account.getBankCode());
    }

    private void validateOffsetAccountNumber(String offsetAccountNumber) throws OffsetAccountValidationException {
        if (StringUtils.isBlank(offsetAccountNumber))
            throw new OffsetAccountValidationException("Offset account number is a required field");
        if (offsetAccountNumber.length() < getNumberMinLength() ||
                offsetAccountNumber.length() > getNumberMaxLength())
            throw new OffsetAccountValidationException("Offset account number's length must be in the interval <"
                    + getNumberMinLength() + ";" + getNumberMaxLength() + ">");
        if (!StringUtils.isNumeric(offsetAccountNumber))
            throw new OffsetAccountValidationException("Offset account number must be a positive numeric value");
    }


    private void validateBankCode(String bankCode
    ) throws OffsetAccountValidationException {
        if (StringUtils.isBlank(bankCode))
            throw new OffsetAccountValidationException("Bank code is a required field");
        if (bankCode.length() != getBankCodeLength())
            throw new OffsetAccountValidationException("Bank code must be " + getBankCodeLength() + " digits");
        if (!StringUtils.isNumeric(bankCode))
            throw new OffsetAccountValidationException("Bank code must be a positive numeric value");
    }
}
