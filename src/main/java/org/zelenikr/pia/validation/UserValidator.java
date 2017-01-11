package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.User;

/**
 * @author Roman Zelenik
 */
public interface UserValidator extends DomainValidator<User> {
    int getUserNameLength();

    int getPasswordLength();
}
