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
 * View servlet responsible for rendering client pages
 *
 * @author Roman Zelenik
 */
@WebServlet("/client")
public class ClientView extends TemplateServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("ClientView.doGet()");
        try {
            Map<String, Object> vars = emptyVariables();
            vars.put(DISPLAY_NAME_PARAMETER, getDisplayName(req));

            resp.setContentType("text/html");
            renderTemplate("user/userpage", vars, resp.getWriter());

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
