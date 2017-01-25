package org.zelenikr.pia.exchange.cnb;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.Currency;
import org.zelenikr.pia.exchange.ExchangeRateManager;
import org.zelenikr.pia.manager.CurrencyManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds exchange rates from CNB. Main currency is CZK. All rates are 1 / x CZK
 *
 * @author Roman Zelenik
 */
public class CNBExchangeRateManager implements ExchangeRateManager {

    private static final int
            CROWNS_INDEX = 2,
            CODE_INDEX = 3,
            RATE_INDEX = 4;

    private Map<Currency, Float> exchangeList;
    private String source;
    private CNBExchangeRateLoader loader;
    private CurrencyManager currencyManager;

    @Autowired
    public CNBExchangeRateManager(String source, CNBExchangeRateLoader loader, CurrencyManager currencyManager) {
        this.source = source;
        this.loader = loader;
        this.currencyManager = currencyManager;
        this.exchangeList = load(source);
        this.exchangeList.put(Currency.CZK, 1f);
    }

    @Override
    public float exchange(Currency from, Currency to, float amount) {
        float exchangedAmount;
        // to CZK
        exchangedAmount = amount * exchangeList.get(from);
        // to dest Currency
        exchangedAmount /= exchangeList.get(to);
        return exchangedAmount;
    }

    private Map<Currency, Float> load(String source) {
        Map<Currency, Float> exchangeList = new HashMap<>();
        try (CNBExchangeRateFileReader reader = loader.getReader(source)) {
            // date
            reader.nextRow();
            // headers
            reader.nextRow();
            String[] values;
            String rateStr, crownsStr;
            float rate;
            while (reader.hasNextRow()) {
                values = reader.nextRow();
                if (isAvailable(values[CODE_INDEX])) {
                    rateStr = values[RATE_INDEX];
                    rateStr = rateStr.replace(',', '.');
                    crownsStr = values[CROWNS_INDEX];
                    rate = Float.parseFloat(rateStr) / Integer.parseInt(crownsStr);
                    exchangeList.put(Currency.valueOf(values[CODE_INDEX]), rate);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return exchangeList;
    }

    private boolean isAvailable(String currencyCode) {
        Currency[] currencies = currencyManager.getAvailableCurrencies();
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].name().equals(currencyCode))
                return true;
        }
        return false;
    }
}
