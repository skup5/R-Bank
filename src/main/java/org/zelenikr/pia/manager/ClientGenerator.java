package org.zelenikr.pia.manager;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.Address;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.CreditCard;
import org.zelenikr.pia.validation.exception.*;

import java.math.BigDecimal;

/**
 * @author Roman Zelenik
 */
@Service
public class ClientGenerator {

    private AddressManager addressManager;
    private CreditCardManager creditCardManager;
    private BankAccountManager bankAccountManager;
    private ClientManager clientManager;

    @Autowired
    public ClientGenerator(AddressManager addressManager, CreditCardManager creditCardManager, BankAccountManager bankAccountManager, ClientManager clientManager) {
        this.addressManager = addressManager;
        this.creditCardManager = creditCardManager;
        this.bankAccountManager = bankAccountManager;
        this.clientManager = clientManager;
    }

    public Client newClientAccount() {
        CreditCard creditCard = new CreditCard(
                RandomStringUtils.randomNumeric(16), 1234);
        try {
            creditCardManager.create(creditCard);
        } catch (CreditCardValidationException e) {
            // clientManager.delete(client);
            throw new RuntimeException(e);
        }

        BankAccount bankAccount = new BankAccount(RandomStringUtils.randomNumeric(9), BigDecimal.ZERO);
        try {
//            bankAccountManager.create(bankAccount, creditCard, client);
            bankAccountManager.create(bankAccount, creditCard, null);
        } catch (BankAccountValidationException e) {
            creditCardManager.delete(creditCard);
            throw new RuntimeException(e);
        }
        Address clientAddress = new Address(1, "street", "city", RandomStringUtils.randomNumeric(5));
        Client client = new Client("John", "Doo", RandomStringUtils.randomNumeric(10), RandomStringUtils.randomNumeric(9), "e@e.com");
        try {
            addressManager.create(clientAddress);
//            clientManager.register(client, clientAddress);
            clientManager.register(client, clientAddress, bankAccount);
        } catch (UserValidationException | PersonValidationException | ClientValidationException e) {
            addressManager.delete(clientAddress);
            bankAccountManager.delete(bankAccount);
            throw new RuntimeException(e);
        }
        return client;
    }
}