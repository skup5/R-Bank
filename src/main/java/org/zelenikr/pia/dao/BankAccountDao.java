package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;

/**
 * @author Roman Zelenik
 */
public interface BankAccountDao extends GenericDao<BankAccount> {

    /**
     * Returns {@link BankAccount} without association fields.
     *
     * @param accountNumber the requested account number
     * @return bank account with the given account number or null
     */
    BankAccount findByAccountNumber(String accountNumber);

    /**
     * Returns {@link BankAccount} associated with {@link Client} owner.
     *
     * @param accountNumber the requested account number
     * @param ownerId       id of the requested {@link Client} owner
     * @return bank account with the given account number and requested owner or null
     */
    BankAccount findByAccountNumberWithOwner(String accountNumber, long ownerId);
}
