package org.zelenikr.pia.web.servlet.spring.controller.client.transaction;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.manager.BankAccountManager;
import org.zelenikr.pia.manager.PaymentTransactionManager;
import org.zelenikr.pia.web.servlet.spring.controller.client.AbstractClientController;
import org.zelenikr.pia.web.servlet.spring.view.client.TransactionListView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Superclass for transaction list controllers.
 * All subclasses use same default view {@link TransactionListView}.
 *
 * @author Roman Zelenik
 */
public abstract class AbstractTransactionListController extends AbstractClientController {

    private static final String VIEW_URL = "/view/client/transaction-list";

    protected static final String
            ACTUAL_BANK_ACCOUNT_SESSION = "actualAccount",
            PAGE_SIZE_SESSION = "selectTransactionPagination";

    protected static final String
            TRANSACTION_LIST_ATTRIBUTE = "transactions",
            ACTUAL_BANK_ACCOUNT_ATTRIBUTE = "actualAccount",
            ACTUAL_PAGE_ATTRIBUTE = "actualPage",
            TRANSACTION_COUNT_ATTRIBUTE = "transactionCount";

    protected static final String
            ACCOUNT_NUMBER_PARAMETER = "selectAccount",
            PAGINATION_SIZE_PARAMETER = "selectTransactionPagination",
            ACTUAL_PAGE_PARAMETER = "page";

    protected static final String PAGE_SIZE_ATTRIBUTE = PAGINATION_SIZE_PARAMETER;

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
        String pageStr = req.getParameter(ACTUAL_PAGE_PARAMETER);
        int page = 1;
        if (StringUtils.isNumeric(pageStr)) {
            page = Integer.parseInt(pageStr);
            page = page == 0 ? 1 : page;
        }
        BankAccount actualAccount = (BankAccount) req.getSession().getAttribute(ACTUAL_BANK_ACCOUNT_SESSION);
        Integer pageSize = (Integer) req.getSession().getAttribute(PAGE_SIZE_SESSION);
        Long count = (Long) req.getSession().getAttribute(getTransactionCountSessionName());
        if (actualAccount != null && pageSize != null) {
            loadStatement(req, actualAccount, pageSize, page);
            if (count == null) {
                count = getTransactionCount(actualAccount);
            }
        }

        dispatch(req, resp, page, count, pageSize, getClientBankAccounts(req));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNumber = req.getParameter(ACCOUNT_NUMBER_PARAMETER);
        String pageSizeStr = req.getParameter(PAGINATION_SIZE_PARAMETER);

        if (!StringUtils.isNumeric(accountNumber) && !StringUtils.isNumeric(pageSizeStr)) {
            doGet(req, resp);
            return;
        }

        int pageSize = Integer.parseInt(pageSizeStr);
        Long count = (Long) req.getSession().getAttribute(getTransactionCountSessionName());
        BankAccount actualAccount = (BankAccount) req.getSession().getAttribute(ACTUAL_BANK_ACCOUNT_SESSION);

        if (actualAccount == null) {
            actualAccount = bankAccountManager.findBy(accountNumber, getAuthenticatedClient(req).getId());
            count = getTransactionCount(actualAccount);
        } else if (!actualAccount.getAccountNumber().equals(accountNumber)) {
            actualAccount = bankAccountManager.findBy(accountNumber, getAuthenticatedClient(req).getId());
            count = getTransactionCount(actualAccount);
        } else if (count == null) {
            count = getTransactionCount(actualAccount);
        }

        req.getSession().setAttribute(ACTUAL_BANK_ACCOUNT_SESSION, actualAccount);
        req.getSession().setAttribute(getTransactionCountSessionName(), count);
        req.getSession().setAttribute(PAGE_SIZE_SESSION, pageSize);

        loadStatement(req, actualAccount, pageSize, 1);

        dispatch(req, resp, 1, count, pageSize, getClientBankAccounts(req));
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp, int page, Long count, Integer pageSize, Set<BankAccount> clientBankAccounts) throws ServletException, IOException {
        req.setAttribute(ACTUAL_PAGE_ATTRIBUTE, page);
        req.setAttribute(TRANSACTION_COUNT_ATTRIBUTE, count);
        req.setAttribute(PAGE_SIZE_ATTRIBUTE, pageSize);
        req.setAttribute(BANK_ACCOUNTS_ATTRIBUTE, clientBankAccounts);
        dispatch(req, resp);
    }

    /**
     * Loads and inserts requested transactions.
     */
    protected abstract void loadStatement(HttpServletRequest req, BankAccount actualAccount, int pageSize, int pageNumber);

    protected abstract long getTransactionCount(BankAccount actualAccount);

    protected abstract String getTransactionCountSessionName();
}
