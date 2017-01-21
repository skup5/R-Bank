package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.BankAccount;

/**
 * @author Roman Zelenik
 */
public interface BankAccountDao extends GenericDao<BankAccount>{

    /**
     *
     * @param accountNumber the requested account number
     * @return bank account with the given account number or null
     */
    BankAccount findByAccountNumber(String accountNumber);

    BankAccount findByAccountNumberWithOwner(String accountNumber, long ownerId);
}
