package org.zelenikr.pia.web.servlet.spring.view;

import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
@WebServlet("/view/login-verification")
public class LoginVerificationView extends AbstractView {

    private static final String
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

        Map<String, Object> vars = emptyVariables();
        vars.put(VERIFICATION_CODE_LENGTH_ATTRIBUTE, req.getAttribute(VERIFICATION_CODE_LENGTH_ATTRIBUTE));
        vars.put(VERIFICATION_CODE_TIMEOUT_ATTRIBUTE, req.getAttribute(VERIFICATION_CODE_TIMEOUT_ATTRIBUTE));
//        vars.put(SUCCESS_ATTRIBUTE, req.getAttribute(SUCCESS_ATTRIBUTE));
        vars.put(ERROR_ATTRIBUTE, req.getAttribute(ERROR_ATTRIBUTE));
        try {
            resp.setContentType("text/html");
            renderTemplate(template, vars, resp.getWriter());
        } catch (TemplateParserException e) {
            throw new ServletException(e);
        }
    }
}
