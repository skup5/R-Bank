package org.danekja.edu.pia.dao;

import org.danekja.edu.pia.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface UserDao extends GenericDao<User>, UserDetailsService {

    /**
     *
     * @param username the requested username
     * @return user with the given username or null
     */
    User findByUsername(String username);

}
