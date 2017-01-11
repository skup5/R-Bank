package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.Role;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface RoleDao extends GenericDao<Role>{
    List<Role> findByUsername(String username);
}
