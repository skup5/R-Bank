package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.BankAccountDao;
import org.zelenikr.pia.domain.BankAccount;

/**
 * @author Roman Zelenik
 */
@Repository
public class BankAccountDaoJpa extends GenericDaoJpa<BankAccount> implements BankAccountDao {

    public BankAccountDaoJpa() {
        super(BankAccount.class);
    }
}
