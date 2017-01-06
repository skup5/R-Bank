package org.zelenikr.pia.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.exception.BankAccountValidationException;
import org.zelenikr.pia.validation.BankAccountValidation;
import org.zelenikr.pia.validation.Validable;

import javax.persistence.*;

/**
 * Entity representing bank account.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_bank_account")
public class BankAccount extends BaseObject implements Validable {

    private BankAccountValidation bankAccountValidation;

    private Integer number;
    /**
     * Account balance
     */
    private Integer sum;
    private Currency currency;
    @Column(name = "credit_card")
    private CreditCard creditCard;
    private Client owner;

    public BankAccount() {
    }

    public BankAccount(Integer number, Integer sum) {
        this.number = number;
        this.sum = sum;
    }

    /*
    ########### API ##################
     */

    @Transient
    @Autowired
    public void setBankAccountValidation(BankAccountValidation bankAccountValidation) {
        this.bankAccountValidation = bankAccountValidation;
    }

    /**
     * @throws BankAccountValidationException in case the bank account is not in valid state.
     */
    @Override
    public void validate() throws BankAccountValidationException {
        validateNumber();
    }

    private void validateNumber() throws BankAccountValidationException {
        if (number == null) throw new BankAccountValidationException("Account number is a required field");
        if (number.toString().length() != bankAccountValidation.getBankAccountNumberLength())
            throw new BankAccountValidationException("Account number must be " + bankAccountValidation.getBankAccountNumberLength() + " digits");
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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    @Embedded
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * OneToOne association between bank account and credit card.
     *
     * @return
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @ManyToOne
    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;

        BankAccount that = (BankAccount) o;

        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BankAccount{");
        sb.append("number=").append(number);
        sb.append(", sum=").append(sum);
        sb.append(", currency=").append(currency);
        sb.append('}');
        return sb.toString();
    }
}
