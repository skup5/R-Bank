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
 * View servlet responsible for rendering client payment order (new order, verification)
 *
 * @author Roman Zelenik
 */
@WebServlet("/view/client/payment-order")
public class PaymentOrderView extends AbstractClientView {

    private static final String
            REQUIRED_INPUTS_ATTRIBUTE = "requiredInputs",
            PREPARED_TRANSACTION_ATTRIBUTE = "preparedTransaction",
            VERIFICATION_CODE_LENGTH_ATTRIBUTE = "verificationCodeLength",
            VERIFICATION_CODE_TIMEOUT_ATTRIBUTE = "verificationCodeTimeout";


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
        vars.put(REQUIRED_INPUTS_ATTRIBUTE, req.getAttribute(REQUIRED_INPUTS_ATTRIBUTE));
        vars.put(PREPARED_TRANSACTION_ATTRIBUTE, req.getAttribute(PREPARED_TRANSACTION_ATTRIBUTE));
        vars.put(VERIFICATION_CODE_LENGTH_ATTRIBUTE, req.getAttribute(VERIFICATION_CODE_LENGTH_ATTRIBUTE));
        vars.put(VERIFICATION_CODE_TIMEOUT_ATTRIBUTE, req.getAttribute(VERIFICATION_CODE_TIMEOUT_ATTRIBUTE));
        vars.put(BANK_ACCOUNTS_ATTRIBUTE, getClientBankAccounts(req));
        vars.put(DISPLAY_NAME_SESSION, getDisplayName(req));
        vars.put(SUCCESS_ATTRIBUTE, req.getAttribute(SUCCESS_ATTRIBUTE));
        vars.put(ERROR_ATTRIBUTE, req.getAttribute(ERROR_ATTRIBUTE));
        try {
            resp.setContentType("text/html");
            renderTemplate(template, vars, resp.getWriter());

//            Enumeration<String> attr = req.getSession().getAttributeNames();
//            while(attr.hasMoreElements()) System.out.println(attr.nextElement());
//            SecurityContext context = (SecurityContext) req.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
//            System.out.println(context);
//            System.out.println(getDisplayName(req));
        } catch (TemplateParserException e) {
//            throw new ServletException("Chyba při načítání požadované stránky");
            throw new ServletException(e);
        }
    }

}
