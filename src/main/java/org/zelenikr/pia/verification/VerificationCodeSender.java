package org.zelenikr.pia.verification;

import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.PaymentTransaction;

/**
 * Interface for sending messages with verification codes.
 *
 * @author Roman Zelenik
 */
public interface VerificationCodeSender {

    /**
     * @param code
     * @param recipient
     * @param transaction
     * @return true if code was successfully sent
     */
    boolean send(String code, Client recipient, PaymentTransaction transaction);
}
