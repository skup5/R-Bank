package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.CreditCard;
import org.zelenikr.pia.validation.exception.CreditCardValidationException;

/**
 * @author Roman Zelenik
 */
public interface CreditCardManager {

    /**
     * Creates new credit card in data store.
     *
     * @param newCreditCard instance with new credit card data, expected not-null value
     */
    void create(CreditCard newCreditCard) throws CreditCardValidationException;

    /**
     * Deletes credit card from data store, if exists.
     *
     * @param creditCard creditCard to delete
     */
    void delete(CreditCard creditCard);
}
