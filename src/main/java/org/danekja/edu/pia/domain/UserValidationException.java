package org.danekja.edu.pia.domain;

/**
 * Exception thrown when User object fails internal state validation.
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class UserValidationException extends Exception {

    public UserValidationException(String message) {
        super(message);
    }

}
