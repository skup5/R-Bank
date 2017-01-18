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

    /**
     * Deletes bank account with specific account number. Deletes also credit card and payment archive.
     *
     * @param accountNumber account number of bank account to remove
     * @return true if bank account was found and removed, false otherwise
     */
    boolean delete(String accountNumber);

    /**
     * Finds bank account with specific account number and owner id
     *
     * @param accountNumber the requested account number
     * @param ownerId       the requested owner id
     * @return bank account with the given account number and owner or null
     */
    BankAccount findBy(String accountNumber, long ownerId);
}
