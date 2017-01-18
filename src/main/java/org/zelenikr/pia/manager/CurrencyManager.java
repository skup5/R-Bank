package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.Currency;

/**
 * @author Roman Zelenik
 */
public interface CurrencyManager {
    Currency[] getAvailableCurrencies();

    float getExchangeRate(Currency from, Currency to);
}
