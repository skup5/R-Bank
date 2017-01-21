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

    @Value("${verification.code.length}")
    private int codeLength;

    @Value("${verification.code.timeout}")
    private int codeTimeout;


    @Override
    public int getCodeLength() {
        return codeLength;
    }

    @Override
    public int getCodeTimeout() {
        return codeTimeout;
    }
}
