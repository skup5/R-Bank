package org.zelenikr.pia.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.User;
import org.zelenikr.pia.validation.exception.UserValidationException;
import org.zelenikr.pia.validation.exception.ValidationException;

/**
 * @author Roman Zelenik
 */
@Service
public class ConfigurableUserValidator implements UserValidator {

    @Value("${validation.user.username.length}")
    private int userNameLength;
    @Value("${validation.user.password.length}")
    private int passwordLength;

    @Override
    public int getUserNameLength() {
        return userNameLength;
    }

    @Override
    public int getPasswordLength() {
        return passwordLength;
    }

    /**
     * Validates that user instance is currently in a valid state.
     *
     * @throws UserValidationException in case the user is not in valid state.
     */
    @Override
    public void validate(User user) throws ValidationException {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
    }

    private void validateUsername(String username) throws UserValidationException {
        if (StringUtils.isBlank(username)) throw new UserValidationException("Username is a required field");
        if (username.length() != getUserNameLength())
            throw new UserValidationException("Username must be " + getUserNameLength() + " chars long");
        if (!StringUtils.isAlphanumeric(username))
            throw new UserValidationException("Username can contain only alphanumeric chars");

    }

    private void validatePassword(String password) throws UserValidationException {
        if (StringUtils.isBlank(password)) throw new UserValidationException("Password is a required field");
        if (password.length() != getPasswordLength())
            throw new UserValidationException("Password must be " + getPasswordLength() + " digits");
        if (!StringUtils.isNumeric(password))
            throw new UserValidationException("Password can contain only digits");
    }

}
