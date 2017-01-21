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
            ACTUAL_BANK_ACCOUNT_ATTRIBUTE = "actualAccount";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String template = (String) req.getAttribute(TEMPLATE_ATTRIBUTE);
        if (template == null)
            throw new NullPointerException();

        Map<String, Object> vars;

        if (req.getAttribute(COPY_PARAMETERS_ATTRIBUTE) != null) {
            vars = createVariablesFromParameters(req);
        } else {
            vars = new HashMap<>();
        }

        vars.put(TRANSACTION_LIST_ATTRIBUTE, req.getAttribute(TRANSACTION_LIST_ATTRIBUTE));
        vars.put(BANK_ACCOUNTS_ATTRIBUTE, req.getAttribute(BANK_ACCOUNTS_ATTRIBUTE));
        vars.put(ACTUAL_BANK_ACCOUNT_ATTRIBUTE, req.getAttribute(ACTUAL_BANK_ACCOUNT_ATTRIBUTE));
        vars.put(DISPLAY_NAME_SESSION, getDisplayName(req));
        vars.put(SUCCESS_ATTRIBUTE, req.getAttribute(SUCCESS_ATTRIBUTE));
        vars.put(ERROR_ATTRIBUTE, req.getAttribute(ERROR_ATTRIBUTE));

        try {
            resp.setContentType("text/html");
            renderTemplate(template, vars, resp.getWriter());
        } catch (TemplateParserException e) {
            throw new ServletException(e);
        }
    }
}
