package org.zelenikr.pia.validation;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.domain.TransactionState;
import org.zelenikr.pia.domain.TransactionType;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;
import org.zelenikr.pia.validation.exception.PaymentTransactionValidationException;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Roman Zelenik
 */
@Service
public class DefaultPaymentTransactionValidator implements PaymentTransactionValidator {

    OffsetAccountValidator offsetAccountValidator;

    @Autowired
    public void setOffsetAccountValidator(OffsetAccountValidator offsetAccountValidator) {
        this.offsetAccountValidator = offsetAccountValidator;
    }

    @Override
    public void validate(PaymentTransaction transaction) throws PaymentTransactionValidationException, OffsetAccountValidationException {
        validateAmount(transaction.getAmount());
        validateDueDate(transaction.getDueDate());
        validateState(transaction.getState());
        validateType(transaction.getType());
        offsetAccountValidator.validate(transaction.getOffsetAccount());
    }

    private void validateType(TransactionType type) throws PaymentTransactionValidationException {
        if(type == null) throw new PaymentTransactionValidationException("Payment tye is a required field");
    }

    private void validateState(TransactionState state) throws PaymentTransactionValidationException {
        if(state == null) throw new PaymentTransactionValidationException("Payment state is a required field");
    }

    private void validateAmount(BigDecimal amount) throws PaymentTransactionValidationException {
        if (amount == null) throw new PaymentTransactionValidationException("Amount is a required field");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new PaymentTransactionValidationException("Amount must be greater then 0");
    }

    private void validateDueDate(Date dueDate) throws PaymentTransactionValidationException {
        if (dueDate == null) throw new PaymentTransactionValidationException("Due date is a required field");
        if (!DateUtils.isSameDay(dueDate, new Date()) && !dueDate.after(new Date()))
            throw new PaymentTransactionValidationException("Due date cannot be before current date");
    }
}
