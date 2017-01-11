package org.zelenikr.pia.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.exception.PayeeAccountValidationException;
import org.zelenikr.pia.validation.PayeeAccountValidation;
import org.zelenikr.pia.validation.Validable;
import org.zelenikr.pia.validation.exception.ValidationException;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Represents bank account payee for payment transaction.
 *
 * @author Roman Zelenik
 */
@Embeddable
public class PayeeAccount implements Validable {

    private PayeeAccountValidation payeeAccountValidation;

    private String payeeAccountNumber;
    private Integer bankCode;

    public PayeeAccount() {
    }

    public PayeeAccount(String payeeAccountNumber, Integer bankCode) {
        this.payeeAccountNumber = payeeAccountNumber;
        this.bankCode = bankCode;
    }

    /*
    ########### API ##################
     */

    @Transient
    @Autowired
    public void setPayeeAccountValidation(PayeeAccountValidation payeeAccountValidation) {
        this.payeeAccountValidation = payeeAccountValidation;
    }

    /**
     * Validates that payee account instance is currently in a valid state.
     *
     * @throws PayeeAccountValidationException in case the payee account is not in valid state.
     */
    @Override
    public void validate() throws ValidationException {
        validatePayeeAccountNumber();
        validateBankCode();
    }

    private void validatePayeeAccountNumber() throws PayeeAccountValidationException {
        if (StringUtils.isBlank(payeeAccountNumber)) throw new PayeeAccountValidationException("Payee account number is a required field");
        if (payeeAccountNumber.length() < payeeAccountValidation.getNumberMinLength() ||
                payeeAccountNumber.length() > payeeAccountValidation.getNumberMaxLength())
            throw new PayeeAccountValidationException("Payee account number's length must be in the interval <"
                    + payeeAccountValidation.getNumberMinLength() + ";" + payeeAccountValidation.getNumberMaxLength() + ">");
        if (!StringUtils.isNumeric(payeeAccountNumber)) throw new PayeeAccountValidationException("Payee account number must be a positive numeric value");
    }


    private void validateBankCode() throws PayeeAccountValidationException {
        if (bankCode == null) throw new PayeeAccountValidationException("Bank code is a required field");
        if (bankCode.toString().length() != payeeAccountValidation.getBankCodeLength())
            throw new PayeeAccountValidationException("Bank code must be " + payeeAccountValidation.getBankCodeLength() + " digits");
        if (bankCode <= 0) throw new PayeeAccountValidationException("Bank code must be a positive value");
    }

    /*
    ########### MAPPINGS #####################
     */

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public Integer getBankCode() {
        return bankCode;
    }

    public void setBankCode(Integer bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayeeAccount)) return false;

        PayeeAccount that = (PayeeAccount) o;

        if (payeeAccountNumber != null ? !payeeAccountNumber.equals(that.payeeAccountNumber) : that.payeeAccountNumber != null) return false;
        return bankCode != null ? bankCode.equals(that.bankCode) : that.bankCode == null;
    }

    @Override
    public int hashCode() {
        int result = payeeAccountNumber != null ? payeeAccountNumber.hashCode() : 0;
        result = 31 * result + (bankCode != null ? bankCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PayeeAccount{");
        sb.append("number=").append(payeeAccountNumber);
        sb.append(", bankCode=").append(bankCode);
        sb.append('}');
        return sb.toString();
    }
}
