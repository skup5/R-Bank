package org.zelenikr.pia.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity representing pattern of payment order.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(
        name = "zelenikr_rbank_pattern_payment_order",
        uniqueConstraints = @UniqueConstraint(columnNames={"name", "owner_id"})
)
public class PatternPaymentOrder extends AbstractPaymentOrder {

    private String name;
    private Client owner;
    private BankAccount ownerAccount;

    public PatternPaymentOrder() {
        super();
    }

    public PatternPaymentOrder(String name, Date dueDate, BigDecimal amount, Currency currency, OffsetAccount offsetAccount, String constSymbol, String variableSymbol, String specificSymbol, String message) {
        super(dueDate, amount, currency, offsetAccount, constSymbol, variableSymbol, specificSymbol, message);
        this.name = name;
    }


    /*
    ########### MAPPINGS #####################
     */

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * ManyToOne association between payment order patterns and client.
     *
     * @return
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public BankAccount getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(BankAccount ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatternPaymentOrder)) return false;
        if (!super.equals(o)) return false;

        PatternPaymentOrder that = (PatternPaymentOrder) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PatternPaymentOrder{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
