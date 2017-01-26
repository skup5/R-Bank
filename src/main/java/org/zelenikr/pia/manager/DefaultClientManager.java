package org.zelenikr.pia.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.dao.BankAccountDao;
import org.zelenikr.pia.dao.ClientDao;
import org.zelenikr.pia.domain.*;
import org.zelenikr.pia.utils.EmailSender;
import org.zelenikr.pia.validation.ClientValidator;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
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
    private BankAccountDao bankAccountDao;
    private EmailSender emailSender;

    @Autowired
    public DefaultClientManager(UserManager userManager, ClientDao clientDao, BankAccountDao bankAccountDao, ClientValidator clientValidator, EmailSender emailSender) {
        this.userManager = userManager;
        this.clientDao = clientDao;
        this.bankAccountDao = bankAccountDao;
        this.clientValidator = clientValidator;
        this.emailSender = emailSender;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void register(Client newClient, Address clientAddress, BankAccount clientBankAccount) throws ClientValidationException, UserValidationException, PersonValidationException {
        if (!newClient.isNew()) {
            throw new RuntimeException("Client already exists, use save method for updates!");
        }
        if (clientAddress.isNew()) {
            throw new RuntimeException("Client's address has not been created. Create it before client registration.");
        }
        if (clientBankAccount.isNew()) {
            throw new RuntimeException("Client's bank account has not been created. Create it before client registration.");
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
        sendRegistrationInfoMail(generatedUser, newClient.getEmail());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public boolean delete(long id) {
        Client client = clientDao.findOne(id);
        if (client == null)
            return false;
        clientDao.remove(client);
        return true;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<Client> getClients() {
        return clientDao.findAll();
    }

    @Override
    public Client loadDetail(Client client) {
        return loadDetail(client.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Override
    public Client loadDetail(long clientIdNumber) {
        return clientDao.findOneFully(clientIdNumber);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Override
    public Client loadWithBankAccounts(long cliendIdNumber) {
        return clientDao.findOneWithAccounts(cliendIdNumber);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @Override
    public void save(Client client) throws ClientValidationException, PersonValidationException {
        if (client.isNew()) {
            throw new ClientValidationException("Client doesn't exist!");
        }
        clientValidator.validate(client);
        clientDao.save(client);
    }

    @Override
    public void addBankAccount(Client client, BankAccount nextBankAccount) throws ClientValidationException {
        client.getBankAccounts().add(nextBankAccount);
        try {
            save(client);
        } catch (PersonValidationException e) {
            client.getBankAccounts().remove(nextBankAccount);
            throw new ClientValidationException(e.getLocalizedMessage());
        }
    }

    @Override
    public void removeBankAccount(Client client, String accountNumber) throws ClientValidationException, BankAccountValidationException {
        if (client.getBankAccounts().size() <= 1) {
            throw new ClientValidationException("Client has to own at least one bank account.");
        }
        BankAccount account = bankAccountDao.findByAccountNumberWithOwner(accountNumber, client.getId());
        if (account == null) {
            throw new BankAccountValidationException("Cannot delete foreign bank account.");
        } else {
            client.getBankAccounts().remove(account);
            bankAccountDao.remove(account);
        }
    }

    private void sendRegistrationInfoMail(User user, String emailAddress){
        StringBuilder message = new StringBuilder("R-Bank - new account\n");
        message.append("Welcome, this is your username: ");
        message.append(user.getUsername());
        message.append(" and password: ");
        message.append(user.getPassword());
        message.append(" for e-banking.");
        emailSender.send(emailAddress, "Welcome", message.toString());
    }
}
