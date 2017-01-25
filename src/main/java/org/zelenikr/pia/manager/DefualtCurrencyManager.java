package org.zelenikr.pia.manager;

import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.Currency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
@Service
public class DefualtCurrencyManager implements CurrencyManager {

    @Override
    public Currency[] getAvailableCurrencies() {
        return Currency.values();
    }

}
