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
 * View servlet responsible for rendering list of client patterns of payment order
 *
 * @author Roman Zelenik
 */
@WebServlet("/view/client/payment-pattern-list")
public class PatternPaymentOrderListView extends AbstractClientView {

    private static final String PATTERNS_LIST_ATTRIBUTE = "paymentPatterns";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String template = (String) req.getAttribute(TEMPLATE_ATTRIBUTE);
        if (template == null)
            throw new NullPointerException();

        Map<String, Object> vars = initVariables(req);
        vars.put(PATTERNS_LIST_ATTRIBUTE, req.getAttribute(PATTERNS_LIST_ATTRIBUTE));

        try {
            resp.setContentType("text/html");
            renderTemplate(template, vars, resp.getWriter());
        } catch (TemplateParserException e) {
            throw new ServletException(e);
        }
    }
}
