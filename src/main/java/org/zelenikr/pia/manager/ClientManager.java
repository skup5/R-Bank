package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.Address;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.BaseObject;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.validation.exception.ClientValidationException;
import org.zelenikr.pia.validation.exception.PersonValidationException;
import org.zelenikr.pia.validation.exception.UserValidationException;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface ClientManager {

    /**
     * Method for registering new client with already created address and bank account
     *
     * @param newClient         instance with new user data, expected not-null value
     * @param clientAddress     already created address, expected not-null value
     * @param clientBankAccount already created bank account, expected not-null value
     * @throws ClientValidationException if the client instance is not in valid state,
     *                                   e.g. required fields are missing
     */
    void register(Client newClient, Address clientAddress, BankAccount clientBankAccount) throws ClientValidationException, PersonValidationException, UserValidationException;

    /**
     * Deletes client with his bank accounts and payment archive.
     *
     * @param client client to remove
     * @throws ClientValidationException if client doesn't exist
     */
    void delete(Client client) throws ClientValidationException;

    /**
     * Returns list of clients without detail informations.
     *
     * @return list of clients or empty list if there are no clients
     */
    List<Client> getClients();

    /**
     * Fills client instance with detail information (like bank accounts or address).
     *
     * @param client instance to be filled
     * @return updated instance
     * @throws ClientValidationException if client doesn't exist
     */
    Client loadDetails(Client client) throws ClientValidationException;

    /**
     * Updates existing client data.
     *
     * @param client instance with modified client data, expected not-null value
     * @throws ClientValidationException if client doesn't exist yet
     */
    void update(Client client) throws ClientValidationException;
}
