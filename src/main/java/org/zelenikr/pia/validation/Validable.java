package org.zelenikr.pia.validation;

/**
 * @author Roman Zelenik
 */
public interface Validable {
    /**
     * Validates the instance.
     *
     * @throws ValidationException in case the instance is not in valid state.
     */
    void validate() throws ValidationException;
}
