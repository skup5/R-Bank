package org.zelenikr.pia.manager;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.dao.UserDao;
import org.zelenikr.pia.domain.Role;
import org.zelenikr.pia.domain.RoleType;
import org.zelenikr.pia.domain.User;
import org.zelenikr.pia.utils.Encoder;
import org.zelenikr.pia.validation.UserValidator;
import org.zelenikr.pia.validation.exception.UserValidationException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Service
@Transactional
public class DefaultUserManager implements UserManager {

    private UserDao userDao;
    private UserValidator userValidator;
    private RoleManager roleManager;
    private Encoder encoder;

    @Autowired
    public DefaultUserManager(UserDao userDao, UserValidator userValidator, RoleManager roleManager, Encoder encoder) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.roleManager = roleManager;
        this.encoder = encoder;
    }

    @Override
    public void register(User newUser, List<RoleType> roles) throws UserValidationException {
        if (!newUser.isNew()) {
            throw new RuntimeException("User already exists, use save method for updates!");
        }

        userValidator.validate(newUser);


        if (!hasUniqueUsername(newUser)) {
            throw new UserValidationException("Username already taken!");
        }

        if (roles.isEmpty()) {
            throw new UserValidationException("User must have at least one role!");
        }

        Set<Role> userRoles = roleManager.saveRoles(roles);
        //for (Role role : userRoles) System.out.println("[" + role.getId() + "] " + role);

        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser = userDao.save(newUser);

        newUser.getRoles().addAll(userRoles);
        userDao.save(newUser);

        //for(Role role : roleDao.findByUsername(newUser.getUsername()))  System.out.println(role);

    }

    @Override
    public User generateUser() {
        return new User(
                RandomStringUtils.randomAlphanumeric(userValidator.getUserNameLength()),
                RandomStringUtils.randomNumeric(userValidator.getPasswordLength())
        );
    }

    @Override
    public boolean hasUniqueUsername(User uniqueUser) {
        return userDao.findByUsername(uniqueUser.getUsername()) == null;
    }
}
