package org.zelenikr.pia.web.servlet.spring.controller.client;

import org.zelenikr.pia.domain.BankAccount;

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
public class AllTransactionListController extends AbstractTransactionListController {

    private static final String TEMPLATE_PATH = "client/allPayments";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNumber = req.getParameter(ACCOUNT_NUMBER_PARAMETER);
        BankAccount actualAccount = bankAccountManager.findBy(accountNumber, getAuthenticatedClient(req).getId());
        req.setAttribute(TRANSACTION_LIST_ATTRIBUTE, transactionManager.findAllByClientAccount(accountNumber));
        req.setAttribute(BANK_ACCOUNTS_ATTRIBUTE, getClientBankAccounts(req));
        req.setAttribute(ACTUAL_BANK_ACCOUNT_ATTRIBUTE, actualAccount);
        req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);
        dispatch(req, resp);
    }

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }
}
