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
    public Client findByUsernameFully(String username) {
        TypedQuery<Client> q = em.createQuery("FROM Client c JOIN FETCH c.bankAccounts JOIN FETCH c.address WHERE c.username = :uname", Client.class);
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
        TypedQuery<Client> q = em.createQuery("SELECT c FROM Client c JOIN FETCH c.address a WHERE c.id = :id", Client.class);
//        Query q = em.createQuery("SELECT DISTINCT c, ba FROM Client c JOIN FETCH c.address LEFT JOIN c.bankAccounts ba WHERE c.id = :id");
        q.setParameter("id", id);
        try {
//            List<Object[]> list = q.getResultList();
//            List list = q.getResultList();
//            for(Object[] objects : list ){
//            for(Object objects : list ){
//                for(Object o : objects){
//                    System.out.println(o);
//                }
//            }
            Client client;
            client =  q.getSingleResult();
//            for (Object o : (Object[]) q.getSingleResult()) {
//                System.out.println(client.getBankAccounts().size());
//            }

//             client = null;
//                    (Client) clients.get(0);
            client.getBankAccounts().size();
            client.getPaymentOrderPatterns().size();
            return client;
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
}
