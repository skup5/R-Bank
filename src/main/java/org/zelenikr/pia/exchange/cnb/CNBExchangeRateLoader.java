package org.zelenikr.pia.exchange.cnb;

import java.io.IOException;

/**
 * @author Roman Zelenik
 */
public interface CNBExchangeRateLoader {

    /**
     * Returns reader for specific exchange rate file from CNB.
     *
     * @param path absolute path (extension is required)
     * @return reader for the given file
     * @throws IOException if an I/O exception occurs
     */
    CNBExchangeRateFileReader getReader(String path) throws IOException;
}
