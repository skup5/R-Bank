package org.zelenikr.pia.web.servlet.spring.view;

import org.springframework.security.core.context.SecurityContext;
import org.zelenikr.pia.web.servlet.spring.TemplateServlet;
import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
public class Client extends TemplateServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("Client.doGet()");
        try {
            resp.setContentType("text/html");
            renderTemplate("user/userpage", emptyVariables(), resp.getWriter());

//            Enumeration<String> attr = req.getSession().getAttributeNames();
//            while(attr.hasMoreElements()) System.out.println(attr.nextElement());
            SecurityContext context = (SecurityContext) req.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
            System.out.println(context);
            System.out.println(req.getSession().getAttribute(DISPLAY_NAME_PARAMETER));
        } catch (TemplateParserException e) {
//            throw new ServletException("Chyba při načítání požadované stránky");
            throw new ServletException(e);
        }
    }

}
