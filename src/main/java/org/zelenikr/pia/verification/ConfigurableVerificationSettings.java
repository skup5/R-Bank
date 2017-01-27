package org.zelenikr.pia.verification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author Roman Zelenik
 */
@PreAuthorize("isAuthenticated()")
@Service
public class ConfigurableVerificationSettings implements VerificationSettings {

    @Value("${verification.transaction.code.length}")
    private int transactionCodeLength;

    @Value("${verification.transaction.code.timeout}")
    private int transactionCodeTimeout;

    @Value("${verification.authentication.code.length}")
    private int authenticationCodeLength;

    @Value("${verification.authentication.code.timeout}")
    private int authenticationCodeTimeout;


    @Override
    public int getTransactionCodeLength() {
        return transactionCodeLength;
    }

    @Override
    public int getTransactionCodeTimeout() {
        return transactionCodeTimeout;
    }

    @Override
    public int getAuthenticationCodeLength() {
        return authenticationCodeLength;
    }

    @Override
    public int getAuthenticationCodeTimeout() {
        return authenticationCodeTimeout;
    }


}
