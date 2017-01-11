package org.zelenikr.pia.domain;

import javax.persistence.*;

/**
 * Entity representing user role.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_role")
public class Role extends BaseObject {

    private RoleType type;

    public Role() {
    }

    public Role(RoleType type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    public RoleType getType(){
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return type == role.type;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("type=").append(type);
        sb.append('}');
        return sb.toString();
    }

}
