package org.zelenikr.pia.web.servlet.spring.controller.client.transaction;

import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.web.servlet.spring.controller.client.transaction.AbstractTransactionListController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet handling list of all outcoming payment transactions request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/client/expenses")
public class ExpenseTransactionsController extends AbstractTransactionListController {

    private static final String TEMPLATE_PATH = "client/outcomingPayments";
    private static final String TRANSACTION_COUNT_SESSION = "expenseTransactionCount";

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void loadStatement(HttpServletRequest req, BankAccount actualAccount, int pageSize, int pageNumber) {
        req.setAttribute(TRANSACTION_LIST_ATTRIBUTE, transactionManager.findAllExpensesByClientAccount(actualAccount.getAccountNumber(), pageSize, pageNumber));
        req.setAttribute(ACTUAL_BANK_ACCOUNT_ATTRIBUTE, actualAccount);
    }

    @Override
    protected long getTransactionCount(BankAccount actualAccount) {
        return transactionManager.countAllExpensesByClientAccount(actualAccount.getAccountNumber());
    }

    @Override
    protected String getTransactionCountSessionName() {
        return TRANSACTION_COUNT_SESSION;
    }
}
