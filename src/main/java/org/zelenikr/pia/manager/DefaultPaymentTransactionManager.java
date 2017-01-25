package org.zelenikr.pia.manager;

import javafx.collections.transformation.SortedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.bankcode.BankCodeManager;
import org.zelenikr.pia.dao.BankAccountDao;
import org.zelenikr.pia.dao.PaymentTransactionDao;
import org.zelenikr.pia.domain.*;
import org.zelenikr.pia.exchange.ExchangeRateManager;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
import org.zelenikr.pia.verification.TransactionVerifier;
import org.zelenikr.pia.verification.VerificationCodeSender;
import org.zelenikr.pia.validation.PaymentTransactionValidator;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;
import org.zelenikr.pia.validation.exception.PaymentTransactionValidationException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Roman Zelenik
 */
@PreAuthorize("isAuthenticated()")
@Service
@Transactional
public class DefaultPaymentTransactionManager implements PaymentTransactionManager {

    private TransactionVerifier transactionVerifier;
    private VerificationCodeSender codeSender;
    private PaymentTransactionDao paymentTransactionDao;
    private PaymentTransactionValidator paymentTransactionValidator;
    private BankAccountDao bankAccountDao;
    private BankCodeManager bankCodeManager;
    private ExchangeRateManager exchangeRateManager;

    @Autowired
    public void setTransactionVerifier(TransactionVerifier transactionVerifier) {
        this.transactionVerifier = transactionVerifier;
    }

    @Autowired
    public void setCodeSender(VerificationCodeSender codeSender) {
        this.codeSender = codeSender;
    }

    @Autowired
    public void setPaymentTransactionDao(PaymentTransactionDao paymentTransactionDao) {
        this.paymentTransactionDao = paymentTransactionDao;
    }

    @Autowired
    public void setPaymentTransactionValidator(PaymentTransactionValidator paymentTransactionValidator) {
        this.paymentTransactionValidator = paymentTransactionValidator;
    }

    @Autowired
    public void setBankAccountDao(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }

    @Autowired
    public void setBankCodeManager(BankCodeManager bankCodeManager) {
        this.bankCodeManager = bankCodeManager;
    }

    @Autowired
    public void setExchangeRateManager(ExchangeRateManager exchangeRateManager) {
        this.exchangeRateManager = exchangeRateManager;
    }

    @Override
    public void preparePayment(PaymentTransaction newPaymentTransaction, Client payer, String payerAccountNumber) throws PaymentTransactionValidationException, OffsetAccountValidationException, BankAccountValidationException {
        // validate
        if (!newPaymentTransaction.isNew()) {
            throw new RuntimeException("Payment already exists, use verify or cancel method.");
        }
        if (payer.isNew()) {
            throw new RuntimeException("Payer of payment doesn't exist!");
        }

        BankAccount payerAccount = bankAccountDao.findByAccountNumberWithOwner(payerAccountNumber, payer.getId());
        if (payerAccount == null) {
            throw new BankAccountValidationException("Payer doesn't own this bank account.");
        }

        paymentTransactionValidator.validate(newPaymentTransaction);

        checkAccountBalance(payerAccount, newPaymentTransaction);

        // insert payment
        newPaymentTransaction.setState(TransactionState.CREATED);
        newPaymentTransaction.setClientAccount(payerAccount);
        newPaymentTransaction = paymentTransactionDao.save(newPaymentTransaction);
        sendNewCode(newPaymentTransaction);
    }

    @Override
    public boolean verifyPayment(long transactionId, String code) throws BankAccountValidationException {
        PaymentTransaction transaction = paymentTransactionDao.findOne(transactionId);

        // check transaction (not null, state, type)
        if (transaction == null) {
            throw new RuntimeException("Payment transaction with this id doesn't exist.");
        }
        if (transaction.getState() != TransactionState.CREATED) {
            throw new IllegalStateException("Payment transaction is in illegal state.");
        }
        if (!transaction.getType().isExpanses()) {
            throw new RuntimeException("Illegal type of payment transaction.");
        }

        BankAccount account = transaction.getClientAccount();
        checkAccountBalance(account, transaction);

        // verify code
        if (transactionVerifier.verifyObject(transaction, code)) {
            transactionVerifier.forgetObject(transaction);

            // exchange amount
            float exchangedAmountF = exchangeRateManager.exchange(
                    transaction.getCurrency(),
                    account.getCurrency(),
                    transaction.getAmount().floatValue()
            );
            BigDecimal exchangedAmount = new BigDecimal(exchangedAmountF);
            account.deduct(exchangedAmount);
            bankAccountDao.save(account);

            // money transfer
            transfer(transaction);

            // change transaction state
//            transaction.setState(TransactionState.WAITING);
            transaction.setState(TransactionState.SENT);

            // amount * (-1)
            transaction.setAmount(exchangedAmount.negate());
            transaction.setCurrency(account.getCurrency());
            paymentTransactionDao.save(transaction);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void cancelPayment(long transactionId) {
        PaymentTransaction transaction = paymentTransactionDao.findOne(transactionId);

        // check transaction (not null, state)
        if (transaction == null) {
//            throw new RuntimeException("Payment transaction with this id doesn't exist.");
            return;
        }
        if (transaction.getState() == TransactionState.CREATED || transaction.getState() == TransactionState.WAITING) {
            transactionVerifier.forgetObject(transaction);
            paymentTransactionDao.remove(transaction);
        } else {
            throw new IllegalStateException("Payment transaction is in illegal state.");
        }
    }

    @Override
    public List<PaymentTransaction> findAllByClientAccount(String accountNumber) {
        return paymentTransactionDao.findByClientAccountNumber(accountNumber, Arrays.asList(TransactionState.RECEIVED, TransactionState.SENT));
    }

    @Override
    public List<PaymentTransaction> findAllRevenuesByClientAccount(String accountNumber) {
        return paymentTransactionDao.findByClientAccountNumber(accountNumber, TransactionState.RECEIVED);
    }

    @Override
    public List<PaymentTransaction> findAllExpensesByClientAccount(String accountNumber) {
        return paymentTransactionDao.findByClientAccountNumber(accountNumber, TransactionState.SENT);
    }

    public void sendNewCode(PaymentTransaction transaction) {
        if (transaction.isNew()) {
            throw new RuntimeException("Payment transaction doesn't exist.");
        }
        // generate code
        String verifyCode = transactionVerifier.generateCode(transaction);

        // send code
        Client payer = transaction.getClientAccount().getOwner();
        if (codeSender.send(verifyCode, payer, transaction)) {
            // successfully sent
        } else {
            throw new RuntimeException("Could not send verify code.");
        }
    }

    private void transfer(PaymentTransaction transaction) {
        if (!bankCodeManager.isOurBank(transaction.getOffsetAccount().getBankCode())) {
            return;
        }
        BankAccount payeeBankAccount = bankAccountDao.findByAccountNumber(transaction.getOffsetAccount().getOffsetAccountNumber());
        if (payeeBankAccount == null) {
            return;
        }
        payeeBankAccount.add(transaction.getAmount());
        bankAccountDao.save(payeeBankAccount);

        BigDecimal amount = transaction.getAmount();
        if (!payeeBankAccount.getCurrency().equals(transaction.getCurrency())) {
            // exchange amount
            amount = new BigDecimal(
                    exchangeRateManager.exchange(
                            transaction.getCurrency(),
                            payeeBankAccount.getCurrency(),
                            amount.floatValue()
                    ));
        }

        PaymentTransaction payeeTransaction = new PaymentTransaction(
                TransactionType.INCOMING_PAYMENT, TransactionState.RECEIVED,
                transaction.getDueDate(), amount, payeeBankAccount.getCurrency(),
                new OffsetAccount(transaction.getClientAccount().getAccountNumber(), bankCodeManager.getBankCode().getCode()),
                transaction.getConstSymbol(), transaction.getVariableSymbol(), transaction.getSpecificSymbol(),
                transaction.getMessage());

        payeeTransaction.setClientAccount(payeeBankAccount);
        paymentTransactionDao.save(payeeTransaction);

    }

    private void checkAccountBalance(BankAccount account, PaymentTransaction transaction) throws BankAccountValidationException {
        // exchange amount
        float exchangedAmount = exchangeRateManager.exchange(
                transaction.getCurrency(),
                account.getCurrency(),
                transaction.getAmount().floatValue()
        );

        // check account state
        if (!account.hasEnough(new BigDecimal(exchangedAmount))) {
            throw new BankAccountValidationException("There isn't enough money in this bank account.");
        }
    }
}
