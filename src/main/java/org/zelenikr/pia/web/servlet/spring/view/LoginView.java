package org.zelenikr.pia.web.servlet.spring.view;

import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * View servlet responsible for rendering page containing login form
 *
 * @author Roman Zelenik
 */
@WebServlet("/login")
public class LoginView extends AbstractView {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            renderTemplate("login", Collections.singletonMap(ERROR_ATTRIBUTE, req.getAttribute(ERROR_ATTRIBUTE)), resp.getWriter());
        } catch (TemplateParserException e) {
//            throw new ServletException("Chyba při načítání požadované stránky");
            throw new ServletException(e);
        }
    }

}
