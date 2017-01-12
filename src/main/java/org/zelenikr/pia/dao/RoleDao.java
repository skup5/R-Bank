package org.zelenikr.pia.dao;

import org.zelenikr.pia.domain.Role;
import org.zelenikr.pia.domain.RoleType;

import java.util.List;

/**
 * @author Roman Zelenik
 */
public interface RoleDao extends GenericDao<Role>{
    List<Role> findByUsername(String username);

    /**
     *
     * @param type the requested role type
     * @return role with the given type or null
     */
    Role findByType(RoleType type);
}
