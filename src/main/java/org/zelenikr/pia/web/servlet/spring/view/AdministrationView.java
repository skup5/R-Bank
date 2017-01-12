package org.zelenikr.pia.web.servlet.spring.view;

import org.zelenikr.pia.web.servlet.spring.TemplateServlet;
import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * View servlet responsible for rendering administration pages
 *
 * @author Roman Zelenik
 */
@WebServlet("/view/adminView")
public class AdministrationView extends AbstractView {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String template = (String) req.getAttribute(TEMPLATE_ATTRIBUTE);
        if(template == null)
            throw new NullPointerException();
        Map<String, Object> vars = createVariablesFromParameters(req);
        vars.put(DISPLAY_NAME_PARAMETER, getDisplayName(req));
        vars.put(ERROR_ATTRIBUTE, req.getAttribute(ERROR_ATTRIBUTE));
        try {
            resp.setContentType("text/html");
            renderTemplate(template, vars, resp.getWriter());
        } catch (TemplateParserException e) {
            throw new ServletException(e);
        }
    }
}
