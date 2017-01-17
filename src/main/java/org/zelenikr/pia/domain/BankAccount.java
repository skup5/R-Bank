package org.zelenikr.pia.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity representing bank account.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_bank_account")
public class BankAccount extends BaseObject {

    private String accountNumber;
    /**
     * Account balance
     */
    private BigDecimal sum;

    private Currency currency;

    private CreditCard creditCard;

    private Client owner;

    private Set<SinglePaymentOrder> singlePaymentOrders;

    public BankAccount() {
        this.singlePaymentOrders = new LinkedHashSet<>();
    }

    public BankAccount(String accountNumber, BigDecimal sum) {
        this();
        this.accountNumber = accountNumber;
        this.sum = sum;
    }


    /*
    ########### MAPPINGS #####################
     */

    @Column(unique = true, nullable = false)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String number) {
        this.accountNumber = number;
    }

    @Column(scale = 2)
    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    @Embedded
    @Enumerated
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

    /**
     * ManyToOne association between bank accounts and client.
     *
     * @return
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    /**
     * OneToMany association between bank account and single payment orders.
     *
     * @return
     */
    @OneToMany(mappedBy = "clientAccount")
    @OrderColumn(name = "dueDate")
    public Set<SinglePaymentOrder> getSinglePaymentOrders() {
        return singlePaymentOrders;
    }

    public void setSinglePaymentOrders(Set<SinglePaymentOrder> singlePaymentOrders) {
        this.singlePaymentOrders = singlePaymentOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;

        BankAccount that = (BankAccount) o;

        return accountNumber != null ? accountNumber.equals(that.accountNumber) : that.accountNumber == null;
    }

    @Override
    public int hashCode() {
        return accountNumber != null ? accountNumber.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BankAccount{");
        sb.append("accountNumber=").append(accountNumber);
        sb.append(", sum=").append(sum);
        sb.append(", currency=").append(currency);
        sb.append('}');
        return sb.toString();
    }
}
