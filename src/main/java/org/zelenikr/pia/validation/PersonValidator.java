package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.Person;
import org.zelenikr.pia.validation.exception.PersonValidationException;

/**
 * @author Roman Zelenik
 */
public interface PersonValidator extends DomainValidator<Person>{
    int getPersonIdNoMinLength();

    int getPersonIdNoMaxLength();

    /**
     * Validates that person instance is currently in a valid state.
     *
     * @throws PersonValidationException in case the person is not in valid state.
     */
    @Override
    void validate(Person domainObject) throws PersonValidationException;
}
