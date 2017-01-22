package org.zelenikr.pia.domain;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity representing one-time payment transaction.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_payment_transaction")
@AttributeOverrides({
        @AttributeOverride(name = "dueDate", column = @Column(nullable = false)),
        @AttributeOverride(name = "amount", column = @Column(nullable = false)),
        @AttributeOverride(name = "clientAccount", column = @Column(nullable = false))
})
@BatchSize(size = 100)
public class PaymentTransaction extends AbstractPaymentOrder {

    private TransactionState state;
    private TransactionType type;

    public PaymentTransaction() {
        super();
    }

    public PaymentTransaction(TransactionType type, Date dueDate, BigDecimal amount, OffsetAccount offsetAccount, String constSymbol, String variableSymbol, String specificSymbol, String message) {
        this(type, TransactionState.CREATED, dueDate, amount, offsetAccount, constSymbol, variableSymbol, specificSymbol, message);
    }

    public PaymentTransaction(TransactionType type, TransactionState state, Date dueDate, BigDecimal amount, OffsetAccount offsetAccount, String constSymbol, String variableSymbol, String specificSymbol, String message) {
        super(dueDate, amount, offsetAccount, constSymbol, variableSymbol, specificSymbol, message);
        this.type = type;
        this.state = state;
    }


    /*
    ########### MAPPINGS #####################
     */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentTransaction)) return false;
        if (!super.equals(o)) return false;

        PaymentTransaction that = (PaymentTransaction) o;

        if (state != that.state) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append("{");
        sb.append("state=").append(state);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
