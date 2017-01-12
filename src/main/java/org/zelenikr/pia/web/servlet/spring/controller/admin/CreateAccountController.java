package org.zelenikr.pia.web.servlet.spring.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman Zelenik
 */
@WebServlet("/admin/create-account")
public class CreateAccountController extends AbstractAdminController {

    private static final String TEMPLATE_VALUE = "admin/createAccount";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setAttribute(ERROR_ATTRIBUTE, "testovaci chyba");
        req.setAttribute(TEMPLATE_ATTRIBUTE, TEMPLATE_VALUE);
        req.getRequestDispatcher(VIEW_NAME).forward(req, resp);
    }
}
