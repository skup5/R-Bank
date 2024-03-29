package org.zelenikr.pia.dao.jpa;

import org.zelenikr.pia.dao.GenericDao;
import org.zelenikr.pia.domain.BaseObject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JPA implementation of the {@link GenericDao} interface.
 * <p>
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class GenericDaoJpa<T extends BaseObject> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;
    private Class<T> persistedType;

    /**
     * @param persistedType type of the entity persisted by this DAO
     */
    public GenericDaoJpa(Class<T> persistedType) {
        this.em = em;
        this.persistedType = persistedType;
    }

    @Override
    public T save(T value) {
        if (value.isNew()) {
            em.persist(value);
            return value;
        } else {
            return em.merge(value);
        }
    }

    @Override
    public T findOne(Long id) {
        return em.find(persistedType, id);
    }

    @Override
    public void remove(T toRemove) {
        if (!toRemove.isNew()) {
            toRemove = findOne(toRemove.getId());
            em.remove(toRemove);
        }
    }
}
