package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.Address;

/**
 * @author Roman Zelenik
 */
public interface AddressManager {

    /**
     * Creates new address in data store.
     *
     * @param newAddress instance with new address data, expected not-null value
     */
    void create(Address newAddress);

    /**
     * Deletes address from data store, if exists.
     *
     * @param address address to delete
     */
    void delete(Address address);
}
