package org.zelenikr.pia.manager;

import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.Currency;

import java.util.List;

/**
 * @author Roman Zelenik
 */
@Service
public class DefualtCurrencyManager implements CurrencyManager {

    @Override
    public Currency[] getAvailableCurrencies(){
        return Currency.values();
    }

    @Override
    public float getExchangeRate(Currency from, Currency to){
//        EMU|euro|1|EUR|27,020
        throw new IllegalStateException("Not implemented yet");
    }
}
