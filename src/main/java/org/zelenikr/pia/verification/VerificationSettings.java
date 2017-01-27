package org.zelenikr.pia.verification;

/**
 * @author Roman Zelenik
 */
public interface VerificationSettings {

    /**
     * characters count
     */
    int getTransactionCodeLength();

    /**
     * minutes
     */
    int getTransactionCodeTimeout();

    /**
     * characters count
     */
    int getAuthenticationCodeLength();

    /**
     * minutes
     */
    int getAuthenticationCodeTimeout();
}
