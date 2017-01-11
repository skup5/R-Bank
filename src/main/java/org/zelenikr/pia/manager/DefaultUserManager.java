package org.zelenikr.pia.manager;

import javax.transaction.Transactional;

import org.zelenikr.pia.dao.UserDao;
import org.zelenikr.pia.domain.User;
import org.zelenikr.pia.domain.exception.UserValidationException;
import org.zelenikr.pia.validation.UserValidator;
import org.zelenikr.pia.validation.ValidationException;
import org.zelenikr.pia.utils.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@Service
@Transactional
public class DefaultUserManager implements UserManager {

    private UserDao userDao;
    private UserValidator userValidator;
    private Encoder encoder;

    @Autowired
    public DefaultUserManager(UserDao userDao, UserValidator userValidator, Encoder encoder) {
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.encoder = encoder;
    }

//    @Override
//    public boolean authenticate(String username, String password) {
//        User u = userDao.findByUsername(username);
//        System.out.println(u);
//        return u != null && encoder.validate(password, u.getPassword());
//    }

    @Override
    public void register(User newUser) throws ValidationException {
        if(!newUser.isNew()) {
            throw new RuntimeException("User already exists, use save method for updates!");
        }

        userValidator.validate(newUser);

        User existinCheck = userDao.findByUsername(newUser.getUsername());
        if(existinCheck != null) {
            throw new UserValidationException("Username already taken!");
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userDao.save(newUser);
    }
}
