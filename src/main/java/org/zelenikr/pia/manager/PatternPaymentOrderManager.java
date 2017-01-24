package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.PatternPaymentOrder;
import org.zelenikr.pia.validation.exception.PatternPaymentOrderValidationException;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface PatternPaymentOrderManager {

    /**
     * Creates new {@link PatternPaymentOrder} in data store.
     *
     * @param newPattern             instance with new pattern of payment order data, expected not-null value
     * @param owner                  already created owner, expected not-null value
     * @param ownerBankAccountNumber number of existing client's {@link BankAccount} used in newPattern
     * @throws PatternPaymentOrderValidationException if the {@link PatternPaymentOrder} instance is not in valid state
     */
    void create(PatternPaymentOrder newPattern, Client owner, String ownerBankAccountNumber) throws PatternPaymentOrderValidationException;

    /**
     * Deletes pattern with specific name and belonging specific owner.
     *
     * @param name  the requested name
     * @param owner owner of pattern with the requested name
     * @return true if was found and deleted pattern with the given name and belonging to {@link Client} with the given id
     */
    boolean delete(String name, Client owner);

    /**
     * Returns all patterns belonging to {@link Client} owner with specific id.
     *
     * @param ownerId the owner requested id
     * @return list of patterns or empty list there are none patterns with the given owner
     */
    List<PatternPaymentOrder> findClientPatterns(long ownerId);

    /**
     * Updates existing modified {@link PatternPaymentOrder} data.
     * Sets to changedPattern specific owner and {@link BankAccount} by accountNumber.
     *
     * @param changedPattern instance with modified data
     * @param owner          already created {@link Client}, expected not-null value
     * @param accountNumber  number of existing client's {@link BankAccount} that will be set to changedPattern
     * @throws PatternPaymentOrderValidationException if the {@link PatternPaymentOrder} instance is not in valid state
     */
    void update(PatternPaymentOrder changedPattern, Client owner, String accountNumber) throws PatternPaymentOrderValidationException;
}
