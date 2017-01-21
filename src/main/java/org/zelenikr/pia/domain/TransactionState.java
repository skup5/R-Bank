package org.zelenikr.pia.domain;

/**
 * Defines {@link PaymentTransaction} states.
 *
 * @author Roman Zelenik
 */
public enum TransactionState {
    /**
     * Is created, but not confirmed.
     */
    CREATED,

    /**
     * Confirmed and verified by client. Now waiting for sending.
     */
    WAITING,

    /**
     * Sent payment.
     */
    SENT,

    /**
     * Incoming payment.
     */
    RECEIVED
}
