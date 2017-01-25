package org.zelenikr.pia.exchange.cnb;

import org.zelenikr.pia.utils.WebFileLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Implementation of {@link CNBExchangeRateLoader} interface
 * for loading exchange rate files from CNB (Czech national bank) via {@link URL}.
 *
 * @author Roman Zelenik
 */
public class WebCNBExchangeRateLoader extends WebFileLoader implements CNBExchangeRateLoader {

    @Override
    public CNBExchangeRateFileReader getReader(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        InputStream is = getInputStream(url);
        return new SimpleCNBExchangeRateFileReader(is);
    }
}
