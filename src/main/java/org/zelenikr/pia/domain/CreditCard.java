package org.zelenikr.pia.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.exception.CreditCardValidationException;
import org.zelenikr.pia.validation.CreditCardValidation;
import org.zelenikr.pia.validation.Validable;
import org.zelenikr.pia.validation.ValidationException;

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
public class CreditCard extends BaseObject implements Validable{

    private CreditCardValidation creditCardValidation;

    private Integer number;
    private Integer pin;
//    private Date validity;


    public CreditCard() {
    }

    public CreditCard(Integer number, Integer pin) {
        this.number = number;
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
     *
     * @throws CreditCardValidationException in case the credit card is not in valid state.
     */
    @Override
    public void validate() throws ValidationException {
        validateNumber();
    }

    private void validateNumber() throws CreditCardValidationException {
        if (number == null) throw new CreditCardValidationException("Credit card number is a required field");
        if (number.toString().length() != creditCardValidation.getCreditCardNumberLength())
            throw new CreditCardValidationException("Credit card number must be " + creditCardValidation.getCreditCardNumberLength() + " digits");
    }

    /*
    ########### MAPPINGS #####################
     */

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

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

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        return pin != null ? pin.equals(that.pin) : that.pin == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (pin != null ? pin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreditCard{");
        sb.append("number=").append(number);
        sb.append(", pin=").append(pin);
        sb.append('}');
        return sb.toString();
    }
}
