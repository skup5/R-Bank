package org.zelenikr.pia.validation;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Roman Zelenik
 */
public class ConfigurablePersonValidation implements PersonValidation {

    @Value("${validation.person.personIdNo.minLength}")
    private int personIdNoMinLength;

    @Value("${validation.person.personIdNo.maxLength}")
    private int personIdNoMaxLength;

    @Override
    public int getPersonIdNoMinLength() {
        return personIdNoMinLength;
    }

    @Override
    public int getPersonIdNoMaxLength() {
        return personIdNoMaxLength;
    }
}