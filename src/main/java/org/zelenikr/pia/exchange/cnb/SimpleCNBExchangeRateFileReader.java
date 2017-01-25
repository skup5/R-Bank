package org.zelenikr.pia.exchange.cnb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Roman Zelenik
 */
public class SimpleCNBExchangeRateFileReader implements CNBExchangeRateFileReader{

    private BufferedReader reader;

    public SimpleCNBExchangeRateFileReader(InputStream stream) {
        this.reader = new BufferedReader(new InputStreamReader(stream));
    }

    @Override
    public boolean hasNextRow() throws IOException {
        return reader.ready();
    }

    @Override
    public String[] nextRow() throws IOException {
        return reader.readLine().split("\\|");
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
