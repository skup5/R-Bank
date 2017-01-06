package org.zelenikr.pia.validation;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Roman Zelenik
 */
public class ConfigurableUserValidation implements UserValidation {

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
}
