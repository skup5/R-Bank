package org.zelenikr.pia.exchange;

import org.zelenikr.pia.domain.Currency;

/**
 * @author Roman Zelenik
 */
public interface ExchangeRateManager {

    /**
     * Exchanges specific {@code amount} which is in {@code from} {@link Currency}
     * and returns it in {@code to} {@link Currency}.
     *
     * @param from currency of original amount
     * @param to wanted currency
     * @param amount original amount
     * @return amount in {@code to} {@link Currency}
     */
    float exchange(Currency from, Currency to, float amount);
}
