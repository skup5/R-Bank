package org.zelenikr.pia.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.dao.BankAccountDao;
import org.zelenikr.pia.dao.PatternPaymentOrderDao;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.PatternPaymentOrder;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.validation.PatternPaymentOrderValidator;
import org.zelenikr.pia.validation.exception.PatternPaymentOrderValidationException;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Roman Zelenik
 */
@PreAuthorize("hasRole('ROLE_CLIENT')")
@Service
@Transactional
public class DefaultPatternPaymentOrderManager implements PatternPaymentOrderManager {

    private PatternPaymentOrderDao patternPaymentOrderDao;
    private PatternPaymentOrderValidator patternPaymentOrderValidator;
    private BankAccountDao bankAccountDao;

    @Autowired
    public void setPatternPaymentOrderDao(PatternPaymentOrderDao patternPaymentOrderDao) {
        this.patternPaymentOrderDao = patternPaymentOrderDao;
    }

    @Autowired
    public void setPatternPaymentOrderValidator(PatternPaymentOrderValidator patternPaymentOrderValidator) {
        this.patternPaymentOrderValidator = patternPaymentOrderValidator;
    }

    @Autowired
    public void setBankAccountDao(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }


    @Override
    public void create(PatternPaymentOrder newPattern, Client owner, String ownerBankAccountNumber) throws PatternPaymentOrderValidationException {
        if(!newPattern.isNew()){
            throw new RuntimeException("Pattern of payment order already exists!");
        }
        if (owner.isNew()) {
            throw new RuntimeException("Owner of pattern doesn't exist!");
        }
        patternPaymentOrderValidator.validate(newPattern);

        if(patternPaymentOrderDao.findByNameAndOwner(newPattern.getName(), owner.getId()) != null){
            throw new PatternPaymentOrderValidationException("Pattern with this name already exists");
        }

        BankAccount ownerBankAccount = bankAccountDao.findByAccountNumber(ownerBankAccountNumber);

        newPattern.setOwnerAccount(ownerBankAccount);
        newPattern.setOwner(owner);
        patternPaymentOrderDao.save(newPattern);
    }

    @Override
    public void createFromTransaction(PaymentTransaction transaction) {

    }

    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public List<PatternPaymentOrder> findClientPatterns(long clientId){
        return patternPaymentOrderDao.findByOwner(clientId);
    }
}
