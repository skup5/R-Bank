package org.zelenikr.pia.validation;

import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.validation.exception.PersonValidationException;
import org.zelenikr.pia.validation.exception.UserValidationException;

/**
 * @author Roman Zelenik
 */
public interface ClientValidator extends DomainValidator<Client> {
    @Override
    void validate(Client domainObject) throws PersonValidationException;
}
