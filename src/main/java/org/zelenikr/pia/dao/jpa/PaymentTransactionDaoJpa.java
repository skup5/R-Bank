package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.PaymentTransactionDao;
import org.zelenikr.pia.domain.PaymentTransaction;

/**
 * JPA implementation of the {@link PaymentTransactionDao} interface.
 *
 * @author Roman Zelenik
 */
@Repository
public class PaymentTransactionDaoJpa extends GenericDaoJpa<PaymentTransaction> implements PaymentTransactionDao {

    public PaymentTransactionDaoJpa(){
        super(PaymentTransaction.class);
    }
}
