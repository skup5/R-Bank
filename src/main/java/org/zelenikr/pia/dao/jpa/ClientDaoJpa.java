package org.zelenikr.pia.dao.jpa;

import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.ClientDao;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * JPA implementation of the {@link ClientDao} interface.
 *
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

    @Override
    public Client findOneFully(long id) {
        System.out.println("ClientDaoJpa.findOneFully");
        TypedQuery<Client> q = em.createQuery("SELECT c FROM Client c JOIN FETCH c.address JOIN FETCH c.bankAccounts WHERE c.id = :id", Client.class);
        q.setParameter("id", id);
        try {
           return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Client> findAll() {
        TypedQuery<Client> q = em.createQuery("SELECT c FROM Client c ORDER BY c.surname, c.name", Client.class);
        try {
            return q.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Client findOneWithAccounts(long id) {
        TypedQuery<Client> q = em.createQuery("SELECT c FROM Client c JOIN FETCH c.bankAccounts WHERE c.id = :id", Client.class);
        q.setParameter("id", id);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
