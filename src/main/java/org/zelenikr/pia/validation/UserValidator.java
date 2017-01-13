package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.User;
import org.zelenikr.pia.validation.exception.UserValidationException;

/**
 * @author Roman Zelenik
 */
public interface UserValidator extends DomainValidator<User> {
    int getUserNameLength();

    int getPasswordLength();

    /**
     * Validates that user instance is currently in a valid state.
     *
     * @throws UserValidationException in case the user is not in valid state.
     */
    @Override
    void validate(User domainObject) throws UserValidationException;
}
