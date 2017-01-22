package org.zelenikr.pia.web.servlet.spring.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.manager.BankAccountManager;
import org.zelenikr.pia.manager.PaymentTransactionManager;
import org.zelenikr.pia.web.servlet.spring.view.client.TransactionListView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Superclass for transaction list controllers.
 * All subclasses use same default view {@link TransactionListView}.
 *
 * @author Roman Zelenik
 */
public abstract class AbstractTransactionListController extends AbstractClientController {

    protected static final String
            TRANSACTION_LIST_ATTRIBUTE = "transactions",
            ACTUAL_BANK_ACCOUNT_ATTRIBUTE = "actualAccount";

    protected static final String ACCOUNT_NUMBER_PARAMETER = "selectAccount";

    private static final String VIEW_URL = "/view/client/transaction-list";

    protected BankAccountManager bankAccountManager;
    protected PaymentTransactionManager transactionManager;

    @Autowired
    public void setBankAccountManager(BankAccountManager bankAccountManager) {
        this.bankAccountManager = bankAccountManager;
    }

    @Autowired
    public void setTransactionManager(PaymentTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    protected String getViewUrl() {
        return VIEW_URL;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
        req.setAttribute(BANK_ACCOUNTS_ATTRIBUTE, getClientBankAccounts(req));
        dispatch(req, resp);
    }
}
