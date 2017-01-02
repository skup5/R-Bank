package org.zelenikr.pia.web.servlet.spring;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
public class HomeServlet extends TemplateServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        renderTemplate("index", resp.getWriter());
    }

    @Override
    protected Map<String, Object> getTemplateVariables() {
        return null;
    }
}
