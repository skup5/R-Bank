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
 * View servlet responsible for rendering client's pattern of payment order (create, edit)
 *
 * @author Roman Zelenik
 */
@WebServlet("/view/client/payment-pattern")
public class PatternPaymentOrderView extends AbstractClientView {

    private static final String
            BANK_CODES_ATTRIBUTE = "bankCodes",
            CURRENCIES_ATTRIBUTE = "currencies",
            BANK_ACCOUNTS_ATTRIBUTE = "bankAccounts",
            PREPARED_PATTERN_ATTRIBUTE = "preparedPatternOrder";


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

        vars.put(BANK_CODES_ATTRIBUTE, req.getAttribute(BANK_CODES_ATTRIBUTE));
        vars.put(CURRENCIES_ATTRIBUTE, req.getAttribute(CURRENCIES_ATTRIBUTE));
        vars.put(BANK_ACCOUNTS_ATTRIBUTE, req.getAttribute(BANK_ACCOUNTS_ATTRIBUTE));
        vars.put(PREPARED_PATTERN_ATTRIBUTE, req.getAttribute(PREPARED_PATTERN_ATTRIBUTE));
        try {
            resp.setContentType("text/html");
            renderTemplate(template, vars, resp.getWriter());
        } catch (TemplateParserException e) {
            throw new ServletException(e);
        }
    }
}
