package org.zelenikr.pia.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.dao.BankAccountDao;
import org.zelenikr.pia.dao.PaymentTransactionDao;
import org.zelenikr.pia.domain.*;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
import org.zelenikr.pia.verification.TransactionVerifier;
import org.zelenikr.pia.verification.VerificationCodeSender;
import org.zelenikr.pia.validation.PaymentTransactionValidator;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;
import org.zelenikr.pia.validation.exception.PaymentTransactionValidationException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Roman Zelenik
 */
@PreAuthorize("isAuthenticated()")
@Service
@Transactional
public class DefaultPaymentTransactionManager implements PaymentTransactionManager {

    private static final String OUR_BANK_CODE = "6666";

    private TransactionVerifier transactinVerifier;
    private VerificationCodeSender codeSender;
    private PaymentTransactionDao paymentTransactionDao;
    private PaymentTransactionValidator paymentTransactionValidator;
    private BankAccountDao bankAccountDao;

    @Autowired
    public void setTransactinVerifier(TransactionVerifier transactinVerifier) {
        this.transactinVerifier = transactinVerifier;
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

    @Override
    public PaymentTransaction preparePayment(PaymentTransaction newPaymentTransaction, Client payer, String payerAccountNumber) throws PaymentTransactionValidationException, OffsetAccountValidationException, BankAccountValidationException {
        //TODO: validate
        if (!newPaymentTransaction.isNew()) {
            throw new RuntimeException("Payment already exists, use verify or cancel method.");
        }
        if (payer.isNew()) {
            throw new RuntimeException("Payer of payment doesn't exist!");
        }
//        if (payerAccount.isNew()) {
//            throw new RuntimeException("Payer's bank account doesn't exist.");
//        }

        BankAccount payerAccount = bankAccountDao.findByAccountNumberWithOwner(payerAccountNumber, payer.getId());
        if (payerAccount == null) {
            throw new BankAccountValidationException("Payer doesn't own this bank account.");
        }

        paymentTransactionValidator.validate(newPaymentTransaction);

        if (!payerAccount.hasEnough(newPaymentTransaction.getAmount())) {
            throw new BankAccountValidationException("There isn't enough money in this bank account.");
        }

        //TODO: insert payment
        newPaymentTransaction.setState(TransactionState.CREATED);
        newPaymentTransaction = paymentTransactionDao.save(newPaymentTransaction);
        newPaymentTransaction.setClientAccount(payerAccount);
        newPaymentTransaction = paymentTransactionDao.save(newPaymentTransaction);
        sendNewCode(newPaymentTransaction);
        return newPaymentTransaction;
    }

    @Override
    public boolean verifyPayment(long transactionId, String code) throws BankAccountValidationException {
        PaymentTransaction transaction = paymentTransactionDao.findOne(transactionId);

        //TODO: check transaction (not null, state, type)
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
        if (!account.hasEnough(transaction.getAmount())) {
            throw new BankAccountValidationException("There isn't enough money in this bank account.");
        }

        //TODO: verify code
        // verified
        if (transactinVerifier.verifyObject(transaction, code)) {
            transactinVerifier.forgetObject(transaction);

            account.deduct(transaction.getAmount());
            bankAccountDao.save(account);
            // money transfer
            transfer(transaction);

            //TODO: change transaction state
//            transaction.setState(TransactionState.WAITING);
            transaction.setState(TransactionState.SENT);

            // TODO: amount * (-1)
            transaction.setAmount(transaction.getAmount().negate());
            paymentTransactionDao.save(transaction);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void cancelPayment(long transactionId) {
        PaymentTransaction transaction = paymentTransactionDao.findOne(transactionId);

        //TODO: check transaction (not null, state)
        if (transaction == null) {
//            throw new RuntimeException("Payment transaction with this id doesn't exist.");
            return;
        }
        if (transaction.getState() == TransactionState.CREATED || transaction.getState() == TransactionState.WAITING) {
            transactinVerifier.forgetObject(transaction);
            paymentTransactionDao.remove(transaction);
        } else {
            throw new IllegalStateException("Payment transaction is in illegal state.");
        }
    }

    @Override
    public List<PaymentTransaction> findAllByClientAccount(String accountNumber) {
        return paymentTransactionDao.findByClientAccountNumber(accountNumber);
    }

    public void sendNewCode(PaymentTransaction transaction) {
        if (transaction.isNew()) {
            throw new RuntimeException("Payment transaction doesn't exist.");
        }
        //TODO: generate code
        String verifyCode = transactinVerifier.generateCode(transaction);

        //TODO: send code
        Client payer = transaction.getClientAccount().getOwner();
        if (codeSender.send(verifyCode, payer, transaction)) {
            // successfully sent
        } else {
            throw new RuntimeException("Could not send verify code.");
        }
    }

    public void sendNewCode(PaymentTransaction transaction, Client payer) {
        if (transaction.isNew()) {
            throw new RuntimeException("Payment transaction doesn't exist.");
        }
        if (payer.isNew()) {
            throw new RuntimeException("Payer of payment doesn't exist!");
        }
        //TODO: generate code
        String verifyCode = transactinVerifier.generateCode(transaction);

        //TODO: send code
        if (codeSender.send(verifyCode, payer, transaction)) {
            // successfully sent
        } else {
            throw new RuntimeException("Could not send verify code.");
        }
    }

    private void transfer(PaymentTransaction transaction) {
        BankAccount payeeBankAccount = bankAccountDao.findByAccountNumber(transaction.getOffsetAccount().getOffsetAccountNumber());
        payeeBankAccount.add(transaction.getAmount());
        bankAccountDao.save(payeeBankAccount);

        PaymentTransaction payeeTransaction = new PaymentTransaction(
                TransactionType.INCOMING_PAYMENT, TransactionState.RECEIVED,
                transaction.getDueDate(), transaction.getAmount(),
                new OffsetAccount(transaction.getClientAccount().getAccountNumber(), OUR_BANK_CODE),
                transaction.getConstSymbol(), transaction.getVariableSymbol(), transaction.getSpecificSymbol(),
                transaction.getMessage());

        payeeTransaction.setClientAccount(payeeBankAccount);
        paymentTransactionDao.save(payeeTransaction);

    }

}
