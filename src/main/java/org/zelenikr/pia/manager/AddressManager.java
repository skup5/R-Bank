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
     * Saves existing modified address data.
     *
     * @param address already created address with modified data, expected not-null value
     */
    void save(Address address);
}
