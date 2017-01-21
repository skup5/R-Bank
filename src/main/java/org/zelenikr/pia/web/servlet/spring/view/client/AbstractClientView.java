package org.zelenikr.pia.web.servlet.spring.view.client;

import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.web.servlet.spring.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author Roman Zelenik
 */
public abstract class AbstractClientView extends AbstractView {

    protected static final String COPY_PARAMETERS_ATTRIBUTE = "copyParams";
    protected static final String BANK_ACCOUNTS_ATTRIBUTE = "bankAccounts";
    private static final String AUTHENTICATED_CLIENT_SESSION = "authenticatedClient";

    protected Client getAuthenticatedClient(HttpServletRequest request) {
        return (Client) request.getSession().getAttribute(AUTHENTICATED_CLIENT_SESSION);
    }

    protected BankAccount[] getClientBankAccounts(HttpServletRequest request){
        Set<BankAccount> bankAccountSet = getAuthenticatedClient(request).getBankAccounts();
        return bankAccountSet.toArray(new BankAccount[bankAccountSet.size()]);
    }
}
