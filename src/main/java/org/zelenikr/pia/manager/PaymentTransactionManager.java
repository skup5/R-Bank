package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;
import org.zelenikr.pia.validation.exception.PaymentTransactionValidationException;

/**
 * @author Roman Zelenik
 */
public interface PaymentTransactionManager {

    PaymentTransaction preparePayment(PaymentTransaction newPaymentTransaction, Client payer, String payerAccountNumber) throws PaymentTransactionValidationException, OffsetAccountValidationException, BankAccountValidationException;

    boolean verifyPayment(long transaction, String code) throws BankAccountValidationException;

    void cancelPayment(long transaction);
}
