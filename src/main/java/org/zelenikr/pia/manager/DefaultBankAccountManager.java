package org.zelenikr.pia.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.dao.BankAccountDao;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.CreditCard;
import org.zelenikr.pia.validation.BankAccountValidator;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;

import javax.transaction.Transactional;

/**
 * @author Roman Zelenik
 */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Service
@Transactional
public class DefaultBankAccountManager implements BankAccountManager {

    private BankAccountDao bankAccountDao;
    private BankAccountValidator bankAccountValidator;

    @Autowired
    public DefaultBankAccountManager(BankAccountDao bankAccountDao, BankAccountValidator bankAccountValidator) {
        this.bankAccountDao = bankAccountDao;
        this.bankAccountValidator = bankAccountValidator;
    }

    @Override
    public void create(BankAccount newBankAccount, CreditCard creditCard) throws BankAccountValidationException {
        if (!newBankAccount.isNew()) {
            throw new RuntimeException("Bank account already exists, use save method for updates!");
        }
        if(creditCard.isNew()){
            throw new RuntimeException("Credit card has not been created. Create it before creation bank account registration.");
        }

        bankAccountValidator.validate(newBankAccount);

        if(bankAccountDao.findByAccountNumber(newBankAccount.getAccountNumber()) != null){
            throw new BankAccountValidationException("Bank account number already taken!");
        }

        bankAccountDao.save(newBankAccount);

        newBankAccount.setCreditCard(creditCard);
        bankAccountDao.save(newBankAccount);
    }

    @Override
    public void save(BankAccount bankAccount) {
        throw new IllegalStateException("Not implemented yet");
    }
}