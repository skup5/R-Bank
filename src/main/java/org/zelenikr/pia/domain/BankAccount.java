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

    private Set<PaymentTransaction> paymentTransactions;

    public BankAccount() {
        this.paymentTransactions = new LinkedHashSet<>();
    }

    public BankAccount(String accountNumber, BigDecimal sum, Currency currency) {
        this();
        this.accountNumber = accountNumber;
        this.sum = sum;
        this.currency = currency;
    }

    /*
    ############### API #####################
     */

    /**
     * Checks if there is enough money.
     *
     * @param amount the amount of the deduction
     * @return true if the amount is lower of equal that actual account balance
     */
    public boolean hasEnough(BigDecimal amount) {
        return sum.compareTo(amount) >= 0;
    }

    /**
     * Deducts the amount from this account.
     *
     * @param amount the amount of the deduction
     * @return false if there isn't enough money
     */
    public boolean deduct(BigDecimal amount) {
        if (hasEnough(amount)) {
            sum = sum.subtract(amount);
            return true;
        }
        return false;
    }

    public void add(BigDecimal amount) {
        sum = sum.add(amount);
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
    @OneToMany(mappedBy = "clientAccount", orphanRemoval = true)
    @OrderColumn(name = "dueDate")
    public Set<PaymentTransaction> getPaymentTransactions() {
        return paymentTransactions;
    }

    public void setPaymentTransactions(Set<PaymentTransaction> paymentTransactions) {
        this.paymentTransactions = paymentTransactions;
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
