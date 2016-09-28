package org.danekja.edu.pia.utils;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface Encoder {

    /**
     * Returns hash of the passed text.
     * @param text
     * @return
     */
    String encode(String text);

    /**
     * Validates that the text is the plaintext form associated with the hash.
     * @param text plaintext form
     * @param hash hash for comparison
     * @return true of correct
     */
    boolean validate(String text, String hash);
}
