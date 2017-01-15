package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.Client;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface ClientDao extends GenericDao<Client> {

    /**
     * Returns client without association fields.
     *
     * @param username the requested username
     * @return client with the given username or null
     */
    Client findByUsername(String username);

    /**
     * Returns client with all association fields.
     *
     * @param username the requested username
     * @return client with the given username or null
     */
    Client findByUsernameFully(String username);

    /**
     * Returns list of clients without association fields.
     *
     * @return list of all client entities or empty list
     */
    List<Client> findAll();
}
