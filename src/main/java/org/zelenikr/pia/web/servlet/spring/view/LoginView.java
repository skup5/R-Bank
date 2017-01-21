package org.zelenikr.pia.web.servlet.spring.view;

import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * View servlet responsible for rendering page containing login form
 *
 * @author Roman Zelenik
 */
@WebServlet("/login")
public class LoginView extends AbstractView {

//    private static final String ERROR_CLIENT_SESSION = "errorClient";

    private static String ERROR_PARAMETER = "error";
    private static String LOGOUT_PARAMETER = "logout";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> vars = emptyVariables();
        Exception error = (Exception) req.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
//        Object clientError = req.getSession().getAttribute(ERROR_CLIENT_SESSION);
        if (req.getParameter(ERROR_PARAMETER) != null && error != null) {
//            vars.put(ERROR_ATTRIBUTE, error.getLocalizedMessage());
            vars.put(ERROR_PARAMETER, true);
        } else if (req.getParameter(LOGOUT_PARAMETER) != null) {
            vars.put(LOGOUT_PARAMETER, true);
        }
//        if(clientError != null){
//            vars.put(ERROR_ATTRIBUTE, clientError);
//        }
        try {
            resp.setContentType("text/html");
            renderTemplate("login", vars, resp.getWriter());
        } catch (TemplateParserException e) {
//            throw new ServletException("Chyba při načítání požadované stránky");
            throw new ServletException(e);
        }
    }

}
