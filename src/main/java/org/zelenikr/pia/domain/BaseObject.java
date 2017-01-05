package org.zelenikr.pia.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@MappedSuperclass
public abstract class BaseObject {

    private Long id;

    /**
     *
     * @return true if the entity hasn't been persisted yet
     */
    @Transient
    public boolean isNew() {
        return id == null;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract String toString();
    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
