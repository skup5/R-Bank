package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.Client;

/**
 * @author Roman Zelenik
 */
public interface ClientDao extends GenericDao<Client> {

    /**
     *
     * @param username the requested username
     * @return client with the given username or null
     */
    Client findByUsername(String username);
}
