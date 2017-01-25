package org.zelenikr.pia.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Implementation of {@link CSVLoader} interface for loading csv files from web servers via {@link URL}.
 *
 * @author Roman Zelenik
 */
public class WebCSVLoader extends WebFileLoader implements CSVLoader {

    @Override
    public CSVFileReader getReader(String fileUrl) throws IOException {
        StringUtils.appendIfMissing(fileUrl, ".csv");
        URL url = new URL(fileUrl);
        return new SimpleCSVFileReader(getInputStream(url));
    }
}
