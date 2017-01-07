package org.zelenikr.pia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity representing user role.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_role")
public class Role extends BaseObject {

    //enum RoleType { ROLE_ADMIN, ROLE_CLIENT }

    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return !(name != null ? !name.equals(role.name) : role.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
