package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.PatternPaymentOrder;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.validation.exception.PatternPaymentOrderValidationException;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface PatternPaymentOrderManager {

    void create(PatternPaymentOrder newPattern, Client owner, String ownerBankAccountNumber) throws PatternPaymentOrderValidationException;

    void createFromTransaction(PaymentTransaction transaction);

    boolean delete(String name, Client owner);

    List<PatternPaymentOrder> findClientPatterns(long clientId);

    void update(PatternPaymentOrder changedPattern, Client owner, String accountNumber) throws PatternPaymentOrderValidationException;
}
