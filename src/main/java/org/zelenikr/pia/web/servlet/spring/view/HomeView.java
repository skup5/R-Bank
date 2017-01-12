package org.zelenikr.pia.web.servlet.spring.view;

import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * View servlet responsible for rendering home page (like index)
 *
 * @author Roman Zelenik
 */
@WebServlet(name = "home", value = "/home")
public class HomeView extends AbstractView {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log("HomeView.do*()");
            String url;
//            Map<String, Object> vars = createVariablesFromParameters(req);
            Map<String, Object> vars = emptyVariables();
            vars.put(DISPLAY_NAME_PARAMETER, getDisplayName(req));
            if ((url = getDisplayNameUrl(req)) == null)
                url = "login";
            vars.put(DISPLAY_NAME_URL_PARAMETER, url);

            resp.setContentType("text/html");
            renderTemplate("index", vars, resp.getWriter());
            //System.out.println(getDisplayName(req));
        } catch (TemplateParserException e) {
//            throw new ServletException("Chyba při načítání požadované stránky");
            throw new ServletException(e);
        }
    }

}
