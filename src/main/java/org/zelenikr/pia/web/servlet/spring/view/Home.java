package org.zelenikr.pia.web.servlet.spring.view;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zelenikr.pia.domain.RoleType;
import org.zelenikr.pia.web.servlet.spring.TemplateServlet;
import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Roman Zelenik
 */
public class Home extends TemplateServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log("Home.do*()");
            String displayNameUrl = getDisplayNameUrl();

            req.setAttribute(DISPLAY_NAME_PARAMETER, getDisplayName(req));
            req.setAttribute(DISPLAY_NAME_URL, displayNameUrl);
            resp.setContentType("text/html");
            renderTemplate("index", createVariablesFromAttributes(req), resp.getWriter());
            //System.out.println(getDisplayName(req));
        } catch (TemplateParserException e) {
//            throw new ServletException("Chyba při načítání požadované stránky");
            throw new ServletException(e);
        }
    }

    private String getDisplayNameUrl(){
        String url = "login";
        Collection authoritires = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(authoritires.contains(RoleType.ROLE_ADMIN.name())) url="admin";
        if(authoritires.contains(RoleType.ROLE_CLIENT.name())) url="client";
        return url;
    }
}
