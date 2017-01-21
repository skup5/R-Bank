package org.zelenikr.pia.verification;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.PaymentTransaction;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of {@link TransactionVerifier} interface.
 *
 * @author Roman Zelenik
 */
@Service
public class AlphaNumericTransactionVerifier implements TransactionVerifier {

    private VerificationSettings settings;
    private Map<Long, String> codes;

    @Autowired
    public AlphaNumericTransactionVerifier(VerificationSettings settings) {
        this.settings = settings;
        this.codes = new HashMap();
    }

    @Override
    public String generateCode(PaymentTransaction transaction) {
        String newCode = RandomStringUtils.randomAlphanumeric(settings.getCodeLength());
        codes.put(transaction.getId(), newCode);
        return newCode;
    }

    @Override
    public boolean verifyObject(PaymentTransaction transaction, String code) {
        if (codes.containsKey(transaction.getId()) && codes.get(transaction.getId()).equals(code)) {
            codes.remove(transaction.getId());
            return true;
        } else
            return false;
    }

    @Override
    public void forgetObject(PaymentTransaction transaction) {
        codes.remove(transaction.getId());
    }
}
