package org.zelenikr.pia.web.servlet.spring.view.client;

import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * View servlet responsible for rendering list of client transactions
 *
 * @author Roman Zelenik
 */
@WebServlet("/view/client/transaction-list")
public class TransactionListView extends AbstractClientView {

    private static final String
            TRANSACTION_LIST_ATTRIBUTE = "transactions",
            BANK_ACCOUNTS_ATTRIBUTE = "bankAccounts",
            ACTUAL_BANK_ACCOUNT_ATTRIBUTE = "actualAccount",
            ACTUAL_PAGE_ATTRIBUTE = "actualPage",
            PAGE_SIZE_ATTRIBUTE = "selectTransactionPagination",
            TRANSACTION_COUNT_ATTRIBUTE = "transactionCount";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String template = (String) req.getAttribute(TEMPLATE_ATTRIBUTE);
        if (template == null)
            throw new NullPointerException();

        Map<String, Object> vars = initVariables(req);

        if (req.getAttribute(COPY_PARAMETERS_ATTRIBUTE) != null) {
            copyParametersToVariables(req, vars);
        }

        vars.put(TRANSACTION_LIST_ATTRIBUTE, req.getAttribute(TRANSACTION_LIST_ATTRIBUTE));
        vars.put(BANK_ACCOUNTS_ATTRIBUTE, req.getAttribute(BANK_ACCOUNTS_ATTRIBUTE));
        vars.put(ACTUAL_BANK_ACCOUNT_ATTRIBUTE, req.getAttribute(ACTUAL_BANK_ACCOUNT_ATTRIBUTE));
        vars.put(ACTUAL_PAGE_ATTRIBUTE, req.getAttribute(ACTUAL_PAGE_ATTRIBUTE));
        vars.put(PAGE_SIZE_ATTRIBUTE, req.getAttribute(PAGE_SIZE_ATTRIBUTE));
        vars.put(TRANSACTION_COUNT_ATTRIBUTE, req.getAttribute(TRANSACTION_COUNT_ATTRIBUTE));
        try {
            resp.setContentType("text/html");
            renderTemplate(template, vars, resp.getWriter());
        } catch (TemplateParserException e) {
            throw new ServletException(e);
        }
    }
}
