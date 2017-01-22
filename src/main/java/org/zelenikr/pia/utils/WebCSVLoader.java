package org.zelenikr.pia.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Implementation of {@link CSVLoader} interface for loading csv files from web servers via {@link URL}.
 *
 * @author Roman Zelenik
 */
public class WebCSVLoader implements CSVLoader {

    @Override
    public CSVFileReader getReader(String fileUrl) throws IOException {
        StringUtils.appendIfMissing(fileUrl, ".csv");
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        return new SimpleCSVFileReader(is);
    }
}
