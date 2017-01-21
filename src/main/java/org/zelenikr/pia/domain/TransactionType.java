package org.zelenikr.pia.domain;

/**
 * Defines types of {@link PaymentTransaction}
 *
 * @author Roman Zelenik
 */
public enum TransactionType {

    CREDIT_CARD_PAYMENT,
    ONE_TIME_PAYMENT_ORDER,
    INCOMING_PAYMENT;

    /**
     * @return true if this TransactionType is kind of revenue
     */
    public boolean isRevenues() {
        return this == CREDIT_CARD_PAYMENT || this == INCOMING_PAYMENT;
    }

    /**
     * @return true if this TransactionType is kind of expanse
     */
    public boolean isExpanses() {
        return this == ONE_TIME_PAYMENT_ORDER;
    }
}
