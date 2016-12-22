package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.BaseObject;

/**
 * Common interface for all DAOs
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface GenericDao<T extends BaseObject> {

    T save(T value);

    T findOne(Long id);

    void remove(T toRemove);

}
