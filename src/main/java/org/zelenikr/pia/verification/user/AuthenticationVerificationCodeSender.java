package org.zelenikr.pia.verification.user;

import org.zelenikr.pia.verification.MessageRecipient;

/**
 * Interface for sending messages with authentication verification codes.
 *
 * @author Roman Zelenik
 */
public interface AuthenticationVerificationCodeSender {

    /**
     *
     * @param code
     * @param recipient
     * @return true if code was successfully sent
     */
    boolean send(String code, MessageRecipient recipient);
}
