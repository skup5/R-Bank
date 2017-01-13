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
    void register(User newUser, List<RoleType> roles) throws UserValidationException;

    /**
     * Creates new user with random generated username and password.
     * This method does not guarantee unique user.
     */
    User generateUser();

    /**
     * Checks if already exist user in data store with uniqueUser username.
     *
     * @param uniqueUser
     * @return true if there is none saved user with uniqueUser username
     */
    boolean hasUniqueUsername(User uniqueUser);
}
