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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void loadStatement(HttpServletRequest req, BankAccount actualAccount){
        req.setAttribute(TRANSACTION_LIST_ATTRIBUTE, transactionManager.findAllByClientAccount(actualAccount.getAccountNumber()));
        req.setAttribute(ACTUAL_BANK_ACCOUNT_ATTRIBUTE, actualAccount);
    }

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }
}
