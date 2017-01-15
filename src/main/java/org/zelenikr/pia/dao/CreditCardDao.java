package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.CreditCard;

/**
 * @author Roman Zelenik
 */
public interface CreditCardDao extends GenericDao<CreditCard> {

    /**
     *
     * @param creditCardNumber the requested card number
     * @return credit card with given number or null
     */
    CreditCard findByCardNumber(String creditCardNumber);
}
