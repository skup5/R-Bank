package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.PaymentTransactionDao;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.domain.TransactionState;

import javax.persistence.NoResultException;
import javax.persistence.Query;
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
    public List<PaymentTransaction> findByClientAccountNumber(String accountNumber, TransactionState state, int startRow, int maxRows) {
        System.out.println("PaymentTransactionDaoJpa.findByClientAccountNumber");
        TypedQuery<PaymentTransaction> q = em.createQuery(
                "SELECT p FROM PaymentTransaction p " +
                        "WHERE p.clientAccount.accountNumber = :an AND p.state = :state " +
                        "ORDER BY p.dueDate DESC ",
                PaymentTransaction.class);
        q.setParameter("an", accountNumber);
        q.setParameter("state", state);
        q.setFirstResult(startRow);
        q.setMaxResults(maxRows);
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

    @Override
    public List<PaymentTransaction> findByClientAccountNumber(String accountNumber, List<TransactionState> states, int startRow, int maxRows) {
        System.out.println("PaymentTransactionDaoJpa.findByClientAccountNumber");
        System.out.println(startRow + "+" + maxRows);
        TypedQuery<PaymentTransaction> q = em.createQuery(
                "SELECT p FROM PaymentTransaction p " +
                        "WHERE p.clientAccount.accountNumber = :an AND p.state IN :states " +
                        "ORDER BY p.dueDate DESC ",
                PaymentTransaction.class);
        q.setParameter("an", accountNumber);
        q.setParameter("states", states);
        q.setFirstResult(startRow);
        q.setMaxResults(maxRows);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public long countByClientAccountNumber(String accountNumber, TransactionState state) {
        System.out.println("PaymentTransactionDaoJpa.countByClientAccountNumber");
        Query q = em.createQuery(
                "SELECT COUNT(p.id) FROM PaymentTransaction p " +
                        "WHERE p.clientAccount.accountNumber = :an AND p.state = :state"
        );
        q.setParameter("an", accountNumber);
        q.setParameter("state", state);
        try {
            return (long) q.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public long countByClientAccountNumber(String accountNumber, List<TransactionState> states) {
        System.out.println("PaymentTransactionDaoJpa.countByClientAccountNumber");
        Query q = em.createQuery(
                "SELECT COUNT(p.id) FROM PaymentTransaction p " +
                        "WHERE p.clientAccount.accountNumber = :an AND p.state IN :states"
        );
        q.setParameter("an", accountNumber);
        q.setParameter("states", states);
        try {
            return (long) q.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }
}
