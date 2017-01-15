package org.zelenikr.pia.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.dao.RoleDao;
import org.zelenikr.pia.domain.Role;
import org.zelenikr.pia.domain.RoleType;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Roman Zelenik
 */
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@Service
@Transactional
public class DefaultRoleManager implements RoleManager {

    private RoleDao roleDao;

    @Autowired
    public DefaultRoleManager(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Set<Role> saveRoles(Collection<RoleType> roleTypes) {
        Role role;
        Set<Role> roles = new LinkedHashSet<>(roleTypes.size());
        for (RoleType type : roleTypes) {
            if ((role = roleDao.findByType(type)) == null) {
                role = new Role(type);
                roleDao.save(role);
            }
            roles.add(role);
        }
        return roles;
    }
}
