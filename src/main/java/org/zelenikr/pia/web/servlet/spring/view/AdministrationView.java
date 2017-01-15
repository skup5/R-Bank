package org.zelenikr.pia.web.servlet.spring.view;

import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * View servlet responsible for rendering administration pages
 *
 * @author Roman Zelenik
 */
@WebServlet("/view/adminView")
public class AdministrationView extends AbstractView {

    private static final String COPY_PARAMETERS_ATTRIBUTE = "copyParams";
    private static final String CLIENTS_ATTRIBUTE = "clients";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String template = (String) req.getAttribute(TEMPLATE_ATTRIBUTE);
        if(template == null)
            throw new NullPointerException();

        Collection<Client> clients = (Collection) req.getAttribute(CLIENTS_ATTRIBUTE);
        Map<String, Object> vars;

        if(req.getAttribute(COPY_PARAMETERS_ATTRIBUTE) != null){
            vars = createVariablesFromParameters(req);
        }
        else{
            vars = new HashMap<>();
        }
        if(clients == null){
            log("empty client list");
            clients = Collections.emptyList();
        }
        vars.put(CLIENTS_ATTRIBUTE, clients);
        vars.put(DISPLAY_NAME_PARAMETER, getDisplayName(req));
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
