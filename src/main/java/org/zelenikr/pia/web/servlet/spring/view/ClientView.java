package org.zelenikr.pia.web.servlet.spring.view;

import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * View servlet responsible for rendering client pages
 *
 * @author Roman Zelenik
 */
@WebServlet("/view/clientView")
public class ClientView extends AbstractView {

    private static final String COPY_PARAMETERS_ATTRIBUTE = "copyParams";
    private static final String BANK_ACCOUNTS_ATTRIBUTE = "bankAccounts";


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
        vars.put(BANK_ACCOUNTS_ATTRIBUTE, req.getAttribute(BANK_ACCOUNTS_ATTRIBUTE));
        vars.put(DISPLAY_NAME_SESSION, getDisplayName(req));
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
