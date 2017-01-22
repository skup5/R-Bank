package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.PaymentTransactionDao;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.domain.TransactionState;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * JPA implementation of the {@link PaymentTransactionDao} interface.
 *
 * @author Roman Zelenik
 */
@Repository
public class PaymentTransactionDaoJpa extends GenericDaoJpa<PaymentTransaction> implements PaymentTransactionDao {

    public PaymentTransactionDaoJpa() {
        super(PaymentTransaction.class);
    }

    @Override
    public List<PaymentTransaction> findByClientAccountNumber(String accountNumber, TransactionState state) {
        TypedQuery<PaymentTransaction> q = em.createQuery(
                "SELECT p FROM PaymentTransaction p " +
                        "WHERE p.clientAccount.accountNumber = :an AND p.state = :state " +
                        "ORDER BY p.dueDate DESC ",
                PaymentTransaction.class);
        q.setParameter("an", accountNumber);
        q.setParameter("state", state);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<PaymentTransaction> findByClientAccountNumber(String accountNumber, List<TransactionState> states) {
        TypedQuery<PaymentTransaction> q = em.createQuery(
                "SELECT p FROM PaymentTransaction p " +
                        "WHERE p.clientAccount.accountNumber = :an AND p.state IN :states " +
                        "ORDER BY p.dueDate DESC ",
                PaymentTransaction.class);
        q.setParameter("an", accountNumber);
        q.setParameter("states", states);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

}
