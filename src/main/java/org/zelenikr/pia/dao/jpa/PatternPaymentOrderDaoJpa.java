package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.PatternPaymentOrderDao;
import org.zelenikr.pia.domain.PatternPaymentOrder;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * JPA implementation of the {@link PatternPaymentOrderDao} interface.
 *
 * @author Roman Zelenik
 */
@Repository
public class PatternPaymentOrderDaoJpa extends GenericDaoJpa<PatternPaymentOrder> implements PatternPaymentOrderDao {

    public PatternPaymentOrderDaoJpa() {
        super(PatternPaymentOrder.class);
    }

    @Override
    public PatternPaymentOrder findByNameAndOwner(String name, long ownerId) {
        TypedQuery<PatternPaymentOrder> q = em.createQuery(
                "SELECT p FROM PatternPaymentOrder p " +
                        "WHERE p.name = :name AND p.owner.id = :ownerId", PatternPaymentOrder.class);
        q.setParameter("name", name);
        q.setParameter("ownerId", ownerId);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<PatternPaymentOrder> findByOwner(long ownerId){
        TypedQuery<PatternPaymentOrder> q = em.createQuery(
                "SELECT p FROM PatternPaymentOrder p " +
                        "WHERE p.owner.id = :ownerId", PatternPaymentOrder.class);
        q.setParameter("ownerId", ownerId);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
