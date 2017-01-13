package org.zelenikr.pia.domain;

import org.zelenikr.pia.validation.Validable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity representing credit card.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_credit_card")
public class CreditCard extends BaseObject {

    private String creditCardNumber;
    private Integer pin;

    public CreditCard() {
    }

    public CreditCard(String creditCardNumber, Integer pin) {
        this.creditCardNumber = creditCardNumber;
        this.pin = pin;
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
