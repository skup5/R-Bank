package org.zelenikr.pia.domain;

import org.apache.commons.lang3.time.DateUtils;
import org.zelenikr.pia.domain.exception.SinglePaymentOrderValidationException;
import org.zelenikr.pia.validation.Validable;
import org.zelenikr.pia.validation.ValidationException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entity representing one-time payment.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_single_payment_order")
@AttributeOverrides({
        @AttributeOverride(name = "dueDate", column = @Column(nullable = false)),
        @AttributeOverride(name = "amount", column = @Column(nullable = false))
})
public class SinglePaymentOrder extends AbstractPaymentOrder implements Validable {

    public SinglePaymentOrder() {
        super();
    }

    public SinglePaymentOrder(Date dueDate, BigDecimal amount, Integer constSymbol, String variableSymbol, String specificSymbol, String message) {
        super(dueDate, amount, constSymbol, variableSymbol, specificSymbol, message);
    }

    /*
    ########### API ##################
     */

    /**
     * Validates that single payment order instance is currently in a valid state.
     *
     * @throws SinglePaymentOrderValidationException in case the single payment order is not in valid state.
     */
    @Override
    public void validate() throws ValidationException {
        validateDueDate();
        validateAmount();
    }

    private void validateAmount() throws SinglePaymentOrderValidationException {
        if (amount == null) throw new SinglePaymentOrderValidationException("Amount is a required field");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new SinglePaymentOrderValidationException("Amount must be greater then 0");
    }

    private void validateDueDate() throws SinglePaymentOrderValidationException {
        if (dueDate == null) throw new SinglePaymentOrderValidationException("Due date is a required field");
        if (!DateUtils.isSameDay(dueDate, new Date()) && !dueDate.after(new Date()))
            throw new SinglePaymentOrderValidationException("Due date cannot be before current date");
    }

}
