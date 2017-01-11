package org.zelenikr.pia.web.servlet.spring.view;

import org.zelenikr.pia.web.servlet.spring.TemplateServlet;
import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman Zelenik
 */
public class Administration extends TemplateServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try {
            renderTemplate("admin/index", emptyVariables(), resp.getWriter());
        } catch (TemplateParserException e) {
            throw new ServletException(e);
        }

    }
}
