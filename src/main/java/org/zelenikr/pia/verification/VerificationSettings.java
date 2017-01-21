package org.zelenikr.pia.verification;

/**
 * @author Roman Zelenik
 */
public interface VerificationSettings {

    /**
     * characters count
     */
    int getCodeLength();

    /**
     * minutes
     */
    int getCodeTimeout();
}
