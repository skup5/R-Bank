package org.zelenikr.pia.domain;

import javax.persistence.*;

/**
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_bank_account")
public class BankAccount extends BaseObject {

    private Integer number;
    /**
     * Account balance
     */
    private Integer sum;
    private Currency currency;
    private CreditCard creditCard;

    public BankAccount() {
    }

    public BankAccount(Integer number, Integer sum, Currency currency, CreditCard creditCard) {
        this.number = number;
        this.sum = sum;
        this.currency = currency;
        this.creditCard = creditCard;
    }

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
    @Column(name = "credit_card")
    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
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
