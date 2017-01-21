package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.domain.TransactionState;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface PaymentTransactionDao extends GenericDao<PaymentTransaction> {

    List<PaymentTransaction> findByClientAccountNumber(String accountNumber);

    List<PaymentTransaction> findByClientAccountNumber(String accountNumber, TransactionState state);
}
