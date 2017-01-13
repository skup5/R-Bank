package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.ClientDao;
import org.zelenikr.pia.domain.Client;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * @author Roman Zelenik
 */
@Repository
public class ClientDaoJpa extends GenericDaoJpa<Client> implements ClientDao {

    public ClientDaoJpa() {
        super(Client.class);
    }

    @Override
    public Client findByUsername(String username) {
        TypedQuery<Client> q = em.createQuery("SELECT c FROM Client c WHERE c.username = :uname", Client.class);
        q.setParameter("uname", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
