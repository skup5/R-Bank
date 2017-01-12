package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.RoleType;
import org.zelenikr.pia.domain.User;
import org.zelenikr.pia.validation.exception.UserValidationException;
import org.zelenikr.pia.validation.exception.ValidationException;

import java.util.List;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface UserManager {

    /**
     * Method for registering a new user.
     *
     * @param newUser instance with new user data, expected not-null value
     * @param roles   list of user roles
     * @throws UserValidationException if the new user data instance is not in valid state,
     *                                 e.g. required fields are missing
     */
    void register(User newUser, List<RoleType> roles) throws ValidationException;

    User create();
}
