package org.zelenikr.pia.web.servlet.spring.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Servlet handling new bank client registration request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/admin/create-account")
public class CreateAccountController extends AbstractAdminController {

    private static final String TEMPLATE_PATH = "admin/createAccount";

    @Override
    protected String getTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doPost()");
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            System.out.println(parameterNames.nextElement());
        }
        dispatch(req, resp);
    }

}
