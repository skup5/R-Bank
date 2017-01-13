package org.zelenikr.pia.validation;

import org.zelenikr.pia.validation.exception.ValidationException;

/**
 * @author Roman Zelenik
 */
@Deprecated
public interface Validable {
    /**
     * Validates the instance.
     *
     * @throws ValidationException in case the instance is not in valid state.
     */
    void validate() throws ValidationException;
}
