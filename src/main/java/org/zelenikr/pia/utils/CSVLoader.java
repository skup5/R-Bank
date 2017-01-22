package org.zelenikr.pia.utils;

import java.io.IOException;

/**
 * @author Roman Zelenik
 */
public interface CSVLoader {

    /**
     * Returns reader for specific csv file.
     *
     * @param filePath absolute path (extension is not required)
     * @return reader for the given file
     * @throws IOException if an I/O exception occurs
     */
    CSVFileReader getReader(String filePath) throws IOException;
}
