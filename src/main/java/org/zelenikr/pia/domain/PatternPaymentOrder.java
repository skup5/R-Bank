package org.zelenikr.pia.domain;

import org.apache.commons.lang3.StringUtils;
import org.zelenikr.pia.domain.exception.PatternPaymentOrderValidationException;
import org.zelenikr.pia.validation.Validable;
import org.zelenikr.pia.validation.exception.ValidationException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity representing pattern of payment order.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_pattern_payment_order")
public class PatternPaymentOrder extends AbstractPaymentOrder implements Validable {

    private String name;

    private Client owner;

    public PatternPaymentOrder() {
        super();
    }

    public PatternPaymentOrder(String name, Date dueDate, BigDecimal amount, Integer constSymbol, String variableSymbol, String specificSymbol, String message) {
        super(dueDate, amount, constSymbol, variableSymbol, specificSymbol, message);
        this.name = name;
    }

    /*
    ########### API ##################
     */

    /**
     * Validates that pattern payment order instance is currently in a valid state.
     *
     * @throws PatternPaymentOrderValidationException in case the pattern of payment order is not in valid state.
     */
    @Override
    public void validate() throws ValidationException {
        validateName();
    }

    private void validateName() throws PatternPaymentOrderValidationException {
        if (StringUtils.isBlank(name)) throw new PatternPaymentOrderValidationException("Name is a required field");
        if (!StringUtils.isAlpha(name))
            throw new PatternPaymentOrderValidationException("Name can contain only Unicode letters");
    }

    /*
    ########### MAPPINGS #####################
     */

    @Column(unique = true, nullable = false)
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
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
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
