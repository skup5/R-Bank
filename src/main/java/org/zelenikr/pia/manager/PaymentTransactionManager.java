package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;
import org.zelenikr.pia.validation.exception.PaymentTransactionValidationException;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface PaymentTransactionManager {

    /**
     * Checks and prepares new {@link PaymentTransaction} waiting for verification.
     *
     * @param newPaymentTransaction instance with new payment data, expected not-null value
     * @param payer                 existing client who wants pay payment
     * @param payerAccountNumber    client's account number from which will money are sent
     * @throws PaymentTransactionValidationException if the {@link PaymentTransaction} instance is not in valid state,
     *                                               e.g. required fields are missing
     * @throws OffsetAccountValidationException
     * @throws BankAccountValidationException        if the {@link BankAccount} is not valid,
     *                                               e.g. not enough money
     */
    void preparePayment(PaymentTransaction newPaymentTransaction, Client payer, String payerAccountNumber) throws PaymentTransactionValidationException, OffsetAccountValidationException, BankAccountValidationException;

    /**
     * Verifies code for specific transaction.
     * If it's ok, saves transaction for processing and returns true.
     *
     * @param transaction id of the requested transaction
     * @param code        verification code for the transaction
     * @return true if transaction is in valid state and verification was successful, false otherwise
     * @throws BankAccountValidationException if the {@link BankAccount} is not valid,
     *                                        e.g. not enough money
     */
    boolean verifyPayment(long transaction, String code) throws BankAccountValidationException;

    /**
     * Cancels {@link PaymentTransaction} waiting for verification or money sending if exists.
     *
     * @param transaction id of requested transaction
     */
    void cancelPayment(long transaction);

    long countAllByClientAccount(String accountNumber);

    long countAllExpensesByClientAccount(String accountNumber);

    long countAllRevenuesByClientAccount(String accountNumber);

    /**
     * Finds all incoming and outcoming payments belonging to specific {@link BankAccount} sorted by date.
     *
     * @param accountNumber account number requested BankAccount
     * @return list of payments or empty list
     */
    List<PaymentTransaction> findAllByClientAccount(String accountNumber);

    List<PaymentTransaction> findAllByClientAccount(String accountNumber, int pageSize, int pageNumber);

    /**
     * Finds all incoming payments and revenues belonging to specific {@link BankAccount} sorted by date.
     *
     * @param accountNumber account number requested BankAccount
     * @return list of payments or empty list
     */
    List<PaymentTransaction> findAllRevenuesByClientAccount(String accountNumber);

    List<PaymentTransaction> findAllRevenuesByClientAccount(String accountNumber, int pageSize, int pageNumber);

    /**
     * Finds all outcoming payments and expenses belonging to specific {@link BankAccount} sorted by date.
     *
     * @param accountNumber account number requested BankAccount
     * @return list of payments or empty list
     */
    List<PaymentTransaction> findAllExpensesByClientAccount(String accountNumber);

    List<PaymentTransaction> findAllExpensesByClientAccount(String accountNumber, int pageSize, int pageNumber);
}
