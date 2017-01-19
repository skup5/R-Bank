package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.CreditCardDao;
import org.zelenikr.pia.domain.CreditCard;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * JPA implementation of the {@link CreditCardDao} interface.
 *
 * @author Roman Zelenik
 */
//@Repository
public class CreditCardDaoJpa extends GenericDaoJpa<CreditCard> implements CreditCardDao {

    public CreditCardDaoJpa() {
        super(CreditCard.class);
    }

    @Override
    public CreditCard findByCardNumber(String creditCardNumber) {
        TypedQuery<CreditCard> q = em.createQuery("SELECT c FROM CreditCard c WHERE c.creditCardNumber = :cnumber", CreditCard.class);
        q.setParameter("cnumber", creditCardNumber);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
