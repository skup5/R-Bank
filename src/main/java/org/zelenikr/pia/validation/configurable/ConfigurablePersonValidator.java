package org.zelenikr.pia.validation.configurable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.Person;
import org.zelenikr.pia.validation.PersonValidator;
import org.zelenikr.pia.validation.exception.PersonValidationException;
import org.zelenikr.pia.validation.exception.UserValidationException;

/**
 * @author Roman Zelenik
 */
@Service
public class ConfigurablePersonValidator implements PersonValidator {

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

    @Override
    public void validate(Person person) throws PersonValidationException, UserValidationException {
        validateName(person.getName());
        validateSurname(person.getSurname());
        validatePersonalIdNumber(person.getPersonalIdNumber());
        validateEmail(person.getEmail());
    }

    private void validateName(String name) throws PersonValidationException {
        if (StringUtils.isBlank(name)) throw new PersonValidationException("Name is a required field");
        if (!StringUtils.isAlpha(name)) throw new PersonValidationException("Name can contain only Unicode letters");
    }

    private void validateSurname(String surname) throws PersonValidationException {
        if (StringUtils.isBlank(surname)) throw new PersonValidationException("Surname is a required field");
        if (!StringUtils.isAlpha(surname))
            throw new PersonValidationException("Surname can contain only Unicode letters");
    }

    private void validatePersonalIdNumber(String personalIdNumber) throws PersonValidationException {
        if (StringUtils.isBlank(personalIdNumber))
            throw new PersonValidationException("Personal identification number is required field");
        if (personalIdNumber.length() < getPersonIdNoMinLength() ||
                personalIdNumber.length() >  getPersonIdNoMaxLength())
            throw new PersonValidationException("Personal identification number's length must be in the interval <"
                    +  getPersonIdNoMinLength() + ";" + getPersonIdNoMaxLength() + ">");
        if (!StringUtils.isNumeric(personalIdNumber))
            throw new PersonValidationException("Personal identification number must be a positive numeric value");
    }

    private void validateEmail(String email) throws PersonValidationException {
        if (StringUtils.isBlank(email)) throw new PersonValidationException("Email is required field");
        if (!EmailValidator.getInstance().isValid(email))
            throw new PersonValidationException("Email is invalid");
    }

}