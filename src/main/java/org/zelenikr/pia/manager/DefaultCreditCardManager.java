package org.zelenikr.pia.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.dao.CreditCardDao;
import org.zelenikr.pia.domain.CreditCard;
import org.zelenikr.pia.validation.exception.CreditCardValidationException;
import org.zelenikr.pia.validation.CreditCardValidator;

import javax.transaction.Transactional;

/**
 * @author Roman Zelenik
 */
@PreAuthorize("isAuthenticated()")
@Service
@Transactional
public class DefaultCreditCardManager implements CreditCardManager{

    private CreditCardDao creditCardDao;
    private CreditCardValidator creditCardValidator;

    @Autowired
    public DefaultCreditCardManager(CreditCardDao creditCardDao, CreditCardValidator creditCardValidator) {
        this.creditCardDao = creditCardDao;
        this.creditCardValidator = creditCardValidator;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void create(CreditCard newCreditCard) throws CreditCardValidationException {
        if (!newCreditCard.isNew()) {
            throw new RuntimeException("Credit card already exists, use save method for updates!");
        }

        creditCardValidator.validate(newCreditCard);

        if(creditCardDao.findByCardNumber(newCreditCard.getCreditCardNumber()) != null){
            throw new CreditCardValidationException("Credit card number already taken!");
        }

        creditCardDao.save(newCreditCard);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void delete(CreditCard creditCard) {
        creditCardDao.remove(creditCard);
    }
}
