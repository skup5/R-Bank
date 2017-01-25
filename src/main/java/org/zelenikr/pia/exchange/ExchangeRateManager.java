package org.zelenikr.pia.exchange;

import org.zelenikr.pia.domain.Currency;

/**
 * @author Roman Zelenik
 */
public interface ExchangeRateManager {

    float exchange(Currency from, Currency to, float amount);
}
