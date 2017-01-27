package org.zelenikr.pia.verification.transaction;

import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.verification.MessageRecipient;

/**
 * Interface for sending messages with transaction verification codes.
 *
 * @author Roman Zelenik
 */
public interface TransactionVerificationCodeSender {

    /**
     * @param code
     * @param recipient
     * @param transaction
     * @return true if code was successfully sent
     */
    boolean send(String code, MessageRecipient recipient, PaymentTransaction transaction);
}
