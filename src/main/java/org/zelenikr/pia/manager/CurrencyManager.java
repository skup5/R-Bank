package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.Currency;

/**
 * @author Roman Zelenik
 */
public interface CurrencyManager {

    /**
     * Returns array of supported currencies by this bank.
     *
     * @return
     */
    Currency[] getAvailableCurrencies();
}
