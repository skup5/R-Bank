package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.validation.exception.ValidationException;

/**
 * @author Roman Zelenik
 */
public interface ClientManager {

    /**
     * Method for registering a new client.
     *
     * @param newClient instance with new user data, expected not-null value
     * @throws ValidationException if the new client data instance is not in valid state,
     *                                 e.g. required fields are missing
     */
    void register(Client newClient) throws ValidationException;

    /**
     * Updates existing client data
     * @param client instance with modified client data, expected not-null value
     * @throws ValidationException if client doesn't exist yet
     */
    void update(Client client) throws ValidationException;

    void delete(Client client);
}
