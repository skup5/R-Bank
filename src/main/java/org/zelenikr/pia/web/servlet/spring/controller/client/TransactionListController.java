package org.zelenikr.pia.web.servlet.spring.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.manager.PaymentTransactionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet handling list of all transactions request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/client/all-payments")
public class TransactionListController extends AbstractClientController {

    private static final String TEMPLATE_PATH = "client/allPayments";
    private static final String VIEW_URL = "/view/client/transaction-list";

    protected static final String
            TRANSACTION_LIST_ATTRIBUTE = "transactions",
            ACTUAL_BANK_ACCOUNT_ATTRIBUTE = "actualAccount";

    private static final String ACCOUNT_NUMBER_PARAMETER = "selectAccount";

    //    private ClientManager clientManager;
    private PaymentTransactionManager transactionManager;

    @Autowired
    public void setTransactionManager(PaymentTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected String getViewUrl() {
        return VIEW_URL;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
        //TODO: get payment list
        req.setAttribute(BANK_ACCOUNTS_ATTRIBUTE, getClientBankAccounts(req));
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNumber = req.getParameter(ACCOUNT_NUMBER_PARAMETER);
        BankAccount actualAccount = null;
        for (BankAccount account : getAuthenticatedClient(req).getBankAccounts()) {
            if (account.getAccountNumber().equals(accountNumber))
                actualAccount = account;
        }
        req.setAttribute(TRANSACTION_LIST_ATTRIBUTE, transactionManager.findAllByClientAccount(accountNumber));
        req.setAttribute(BANK_ACCOUNTS_ATTRIBUTE, getClientBankAccounts(req));
        req.setAttribute(ACTUAL_BANK_ACCOUNT_ATTRIBUTE, actualAccount);
        req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);
        dispatch(req, resp);
    }
}
