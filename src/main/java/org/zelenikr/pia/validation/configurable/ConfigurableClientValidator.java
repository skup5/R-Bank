package org.zelenikr.pia.validation.configurable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.validation.ClientValidator;
import org.zelenikr.pia.validation.PersonValidator;
import org.zelenikr.pia.validation.exception.PersonValidationException;
import org.zelenikr.pia.validation.exception.UserValidationException;

/**
 * @author Roman Zelenik
 */
@Service
public class ConfigurableClientValidator implements ClientValidator {

    private PersonValidator personValidator;

    @Autowired
    public ConfigurableClientValidator(PersonValidator personValidator) {
        this.personValidator = personValidator;
    }

    @Override
    public void validate(Client client) throws PersonValidationException {
        personValidator.validate(client);
    }
}
