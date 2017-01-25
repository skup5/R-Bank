package org.zelenikr.pia.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple class for loading files from web servers via {@link URL}.
 *
 * @author Roman Zelenik
 */
public class WebFileLoader {

    /**
     * Returns an input stream that reads from file in specific url.
     *
     * @param url absolute file path on server
     * @return an input stream that reads from file in the given url
     * @throws IOException if an I/O error occurs while creating the input stream
     */
    public InputStream getInputStream(URL url) throws IOException {
        Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "open connection to: " + url);
        URLConnection connection = url.openConnection();
        return connection.getInputStream();
    }
}
