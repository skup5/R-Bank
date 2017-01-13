package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.CreditCardDao;
import org.zelenikr.pia.domain.CreditCard;

/**
 * @author Roman Zelenik
 */
@Repository
public class CreditCardDaoJpa extends GenericDaoJpa<CreditCard> implements CreditCardDao {

    public CreditCardDaoJpa() {
        super(CreditCard.class);
    }
}
