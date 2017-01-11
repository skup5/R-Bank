package org.zelenikr.pia.dao;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.domain.Role;
import org.zelenikr.pia.domain.User;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.List;

/**
 * @author Roman Zelenik
 */
@Repository
public class RoleDaoJpa extends GenericDaoJpa<Role> implements RoleDao {

    public RoleDaoJpa() {
        super(Role.class);
    }

    @Override
    public List<Role> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get("username"), username));
        SetJoin<User, Role> answers = userRoot.joinSet("roles");
        CriteriaQuery<Role> cq = criteriaQuery.select(answers);
        TypedQuery<Role> query = em.createQuery(cq);
        return query.getResultList();
    }
}
