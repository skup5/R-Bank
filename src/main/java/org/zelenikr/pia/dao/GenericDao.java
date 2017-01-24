package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.BaseObject;

/**
 * Common interface for all DAOs
 * <p>
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface GenericDao<T extends BaseObject> {

    T save(T value);

    /**
     * @return entity T or null
     */
    T findOne(Long id);

    void remove(T toRemove);

}
