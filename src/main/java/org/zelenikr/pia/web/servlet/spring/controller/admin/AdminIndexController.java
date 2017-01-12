package org.zelenikr.pia.web.servlet.spring.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Sends redirect response to 'admin index' url.
 *
 * @author Roman Zelenik
 */
@WebServlet("/admin")
public class AdminIndexController extends AbstractAdminController {

    private static final String INDEX_URL = "admin/account-list";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(INDEX_URL);
    }
}
