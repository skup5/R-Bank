package org.zelenikr.pia.domain;

/**
 * Defines types of {@link PaymentTransaction}
 *
 * @author Roman Zelenik
 */
public enum TransactionType {

    ATM_WITHDRAWAL,
    CREDIT_CARD_PAYMENT,
    ONE_TIME_PAYMENT_ORDER,
    INCOMING_PAYMENT;

    /**
     * @return true if this TransactionType is kind of revenue
     */
    public boolean isRevenues() {
        return this == INCOMING_PAYMENT;
    }

    /**
     * @return true if this TransactionType is kind of expanse
     */
    public boolean isExpanses() {
        return !isRevenues();
    }

    public String toLocaleString() {
        switch (this) {
            case ATM_WITHDRAWAL:
                return "Výběr z bankomatu";
            case CREDIT_CARD_PAYMENT:
                return "Transakce platební kartou";
            case INCOMING_PAYMENT:
                return "Došlá platba";
            case ONE_TIME_PAYMENT_ORDER:
                return "Jednorázový platební příkaz";
        }
        return "";
    }
}
