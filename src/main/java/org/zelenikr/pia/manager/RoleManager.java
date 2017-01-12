package org.zelenikr.pia.manager;

import org.zelenikr.pia.domain.Role;
import org.zelenikr.pia.domain.RoleType;

import java.util.Collection;
import java.util.Set;

/**
 * @author Roman Zelenik
 */
public interface RoleManager {

    /**
     * Saves new role types.
     *
     * @param roleTypes
     * @return set of roles with given role types
     */
    Set<Role> saveRoles(Collection<RoleType> roleTypes);
}
