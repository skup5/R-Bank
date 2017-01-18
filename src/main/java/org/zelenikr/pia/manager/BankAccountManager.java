package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.CreditCard;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;

/**
 * @author Roman Zelenik
 */
public interface BankAccountManager {

    /**
     * Creates new bank account in data store.
     *
     * @param newBankAccount instance with new bank account data, expected not-null value
     * @param creditCard     already created credit card, expected not-null value
     */
    void create(BankAccount newBankAccount, CreditCard creditCard) throws BankAccountValidationException;

    /**
     * Saves existing modified bank account.
     *
     * @param bankAccount already created bank account with modified data, expected not-null value
     */
    void save(BankAccount bankAccount);

    /**
     * Deletes bank account from data store, if exists.
     *
     * @param bankAccount bank account to delete
     */
    void delete(BankAccount bankAccount);
}
