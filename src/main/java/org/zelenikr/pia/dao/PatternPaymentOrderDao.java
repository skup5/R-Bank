package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.PatternPaymentOrder;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface PatternPaymentOrderDao extends GenericDao<PatternPaymentOrder> {

    /**
     * Returns {@link PatternPaymentOrder} with specific name and {@link Client} owner id.
     *
     * @param name    the requested name
     * @param ownerId the requested {@link Client} id
     * @return instance with the given name and id or null
     */
    PatternPaymentOrder findByNameAndOwner(String name, long ownerId);

    /**
     * Returns list of patterns belonging to specific {@link Client} owner.
     *
     * @param ownerId the requested {@link Client} id
     * @return list of {@link PatternPaymentOrder} entities of empty list
     */
    List<PatternPaymentOrder> findWithAccountByOwner(long ownerId);

    /**
     * Removes {@link PatternPaymentOrder} with specific name and {@link Client} owner id.
     *
     * @param name    the requested name
     * @param ownerId the requested {@link Client} id
     * @return count of affected rows
     */
    int remove(String name, long ownerId);
}
