package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.BankAccountDao;
import org.zelenikr.pia.domain.BankAccount;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * JPA implementation of the {@link BankAccountDao} interface.
 *
 * @author Roman Zelenik
 */
@Repository
public class BankAccountDaoJpa extends GenericDaoJpa<BankAccount> implements BankAccountDao {

    public BankAccountDaoJpa() {
        super(BankAccount.class);
    }

    @Override
    public BankAccount findByAccountNumber(String accountNumber) {
        TypedQuery<BankAccount> q = em.createQuery("SELECT b FROM BankAccount b WHERE b.accountNumber = :bano", BankAccount.class);
        q.setParameter("bano", accountNumber);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public BankAccount findByAccountNumberWithOwner(String accountNumber, long ownerId) {
        TypedQuery<BankAccount> q = em.createQuery("SELECT b FROM BankAccount b JOIN FETCH b.owner o WHERE b.accountNumber = :bano AND o.id  = :owner", BankAccount.class);
        q.setParameter("bano", accountNumber);
        q.setParameter("owner", ownerId);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
