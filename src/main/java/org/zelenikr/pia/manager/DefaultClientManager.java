package org.zelenikr.pia.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.dao.ClientDao;
import org.zelenikr.pia.domain.*;
import org.zelenikr.pia.validation.ClientValidator;
import org.zelenikr.pia.validation.exception.ClientValidationException;
import org.zelenikr.pia.validation.exception.PersonValidationException;
import org.zelenikr.pia.validation.exception.UserValidationException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * @author Roman Zelenik
 */
@Service
@Transactional
public class DefaultClientManager implements ClientManager {

    private static final int GENERATING_ATTEMPTS = 10;

    private UserManager userManager;
    private ClientDao clientDao;
    private ClientValidator clientValidator;

    @Autowired
    public DefaultClientManager(UserManager userManager, ClientDao clientDao, ClientValidator clientValidator) {
        this.userManager = userManager;
        this.clientDao = clientDao;
        this.clientValidator = clientValidator;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void register(Client newClient, Address clientAddress, BankAccount clientBankAccount) throws ClientValidationException, UserValidationException, PersonValidationException {
        if (!newClient.isNew()) {
            throw new RuntimeException("Client already exists, use save method for updates!");
        }
        if (clientAddress.isNew()){
            throw new RuntimeException("Client address has not been created. Create it before client registration.");
        }
        if (clientBankAccount.isNew()){
            throw new RuntimeException("Client bank account has not been created. Create it before client registration.");
        }

        User generatedUser = null;
        boolean isUnique = false;
        for (int i = 0; i < GENERATING_ATTEMPTS && !isUnique; i++) {
            generatedUser = userManager.generateUser();
            if (userManager.hasUniqueUsername(generatedUser)) {
                isUnique = true;
            }
        }
        if (!isUnique) {
            throw new ClientValidationException("Cannot find unique username for client.");
        }

        newClient.setUsername(generatedUser.getUsername());
        newClient.setPassword(generatedUser.getPassword());
        clientValidator.validate(newClient);

        userManager.register(newClient, Collections.singletonList(RoleType.ROLE_CLIENT));

        newClient = clientDao.save(newClient);

        newClient.getBankAccounts().add(clientBankAccount);
        newClient.setAddress(clientAddress);
        clientDao.save(newClient);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void delete(Client client) throws ClientValidationException {
        throw new IllegalStateException("Not implemented yet");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<Client> getClients() {
        throw new IllegalStateException("Not implemented yet");
    }

    @Override
    public Client loadDetails(Client client) throws ClientValidationException {
        throw new IllegalStateException("Not implemented yet");
    }

    @Override
    public void update(Client client) throws ClientValidationException {
        throw new IllegalStateException("Not implemented yet");
    }
}
