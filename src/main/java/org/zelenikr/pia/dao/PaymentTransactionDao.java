package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.domain.TransactionState;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface PaymentTransactionDao extends GenericDao<PaymentTransaction> {

    /**
     * Returns list of payments in specific {@link TransactionState} and
     * belonging to {@link BankAccount} with specific account number.
     *
     * @param accountNumber account number requested {@link BankAccount}
     * @param state the requested {@link TransactionState}
     * @return list of payments entities or empty list
     */
    List<PaymentTransaction> findByClientAccountNumber(String accountNumber, TransactionState state);

    /**
     * Returns list of payments in one of specific {@link TransactionState} and
     * belonging to {@link BankAccount} with specific account number.
     *
     * @param accountNumber account number requested {@link BankAccount}
     * @param states list of possible states of payment
     * @return list of payments entities or empty list
     */
    List<PaymentTransaction> findByClientAccountNumber(String accountNumber, List<TransactionState> states);

}
