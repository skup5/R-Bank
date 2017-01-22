package org.zelenikr.pia.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Roman Zelenik
 */
public interface CSVFileReader extends Closeable {

    /**
     * Returns true if there is another row in the input of this reader.
     *
     * @return true if and only if this reader has another row of input
     * @throws IOException if an I/O error occurs
     */
    boolean hasNextRow() throws IOException;

    /**
     * Returns next row from the input of this reader like array of values.
     *
     * @return array of values from next row
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException there isn't any next row in the input
     */
    String[] nextRow() throws IOException;

    @Override
    void close() throws IOException;
}
