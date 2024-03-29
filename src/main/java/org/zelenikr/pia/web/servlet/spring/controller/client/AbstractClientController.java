package org.zelenikr.pia.web.servlet.spring.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.User;
import org.zelenikr.pia.manager.ClientManager;
import org.zelenikr.pia.web.servlet.spring.controller.AbstractController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Abstract controller for all client's controllers
 *
 * @author Roman Zelenik
 */
public abstract class AbstractClientController extends AbstractController {

    private static final String AUTHENTICATED_CLIENT_SESSION = "authenticatedClient";

    protected static final String
            COPY_PARAMETERS_ATTRIBUTE = "copyParams",
            BANK_ACCOUNTS_ATTRIBUTE = "bankAccounts";

    protected ClientManager clientManager;

    @Autowired
    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    /**
     * @return this authenticated client
     */
    protected Client getAuthenticatedClient(HttpServletRequest request) {
        return (Client) request.getSession().getAttribute(AUTHENTICATED_CLIENT_SESSION);
    }

    protected Set<BankAccount> getClientBankAccounts(HttpServletRequest request) {
        return getAuthenticatedClient(request).getBankAccounts();
    }
}
