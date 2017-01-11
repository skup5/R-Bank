package org.zelenikr.pia.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.exception.CreditCardValidationException;
import org.zelenikr.pia.validation.CreditCardValidation;
import org.zelenikr.pia.validation.Validable;
import org.zelenikr.pia.validation.exception.ValidationException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity representing credit card.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_credit_card")
public class CreditCard extends BaseObject implements Validable {

    private CreditCardValidation creditCardValidation;

    private String creditCardNumber;
    private Integer pin;

    public CreditCard() {
    }

    public CreditCard(String creditCardNumber, Integer pin) {
        this.creditCardNumber = creditCardNumber;
        this.pin = pin;
    }

    /*
    ########### API ##################
     */

    @Transient
    @Autowired
    public void setCreditCardValidation(CreditCardValidation creditCardValidation) {
        this.creditCardValidation = creditCardValidation;
    }

    /**
     * Validates that credit card instance is currently in a valid state.
     *
     * @throws CreditCardValidationException in case the credit card is not in valid state.
     */
    @Override
    public void validate() throws ValidationException {
        validateCreditCardNumber();
    }

    private void validateCreditCardNumber() throws CreditCardValidationException {
        if (StringUtils.isBlank(creditCardNumber))
            throw new CreditCardValidationException("Credit card number is a required field");
        if (creditCardNumber.length() != creditCardValidation.getCreditCardNumberLength())
            throw new CreditCardValidationException("Credit card number must be " + creditCardValidation.getCreditCardNumberLength() + " digits");
        if (!StringUtils.isNumeric(creditCardNumber))
            throw new CreditCardValidationException("Credit card number must be a positive numeric value");
    }

    /*
    ########### MAPPINGS #####################
     */

    @Column(unique = true, nullable = false)
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Column(precision = 4)
    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;

        CreditCard that = (CreditCard) o;

        if (creditCardNumber != null ? !creditCardNumber.equals(that.creditCardNumber) : that.creditCardNumber != null)
            return false;
        return pin != null ? pin.equals(that.pin) : that.pin == null;
    }

    @Override
    public int hashCode() {
        int result = creditCardNumber != null ? creditCardNumber.hashCode() : 0;
        result = 31 * result + (pin != null ? pin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreditCard{");
        sb.append("number=").append(creditCardNumber);
        sb.append(", pin=").append(pin);
        sb.append('}');
        return sb.toString();
    }
}
