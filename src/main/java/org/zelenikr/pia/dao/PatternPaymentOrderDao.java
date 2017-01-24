package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.PatternPaymentOrder;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface PatternPaymentOrderDao extends GenericDao<PatternPaymentOrder> {
    PatternPaymentOrder findByNameAndOwner(String name, long ownerId);

    List<PatternPaymentOrder> findWithAccountByOwner(long ownerId);

    int remove(String name, long ownerId);
}
