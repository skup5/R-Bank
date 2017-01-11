package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.BaseObject;

/**
 * @author Roman Zelenik
 */
public interface DomainValidator<T extends BaseObject> {
    /**
     * Validates that instance is currently in a valid state.
     *
     * @throws ValidationException in case the instance is not in valid state.
     */
    void validate(T domainObject) throws ValidationException;
}
