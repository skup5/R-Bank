package org.zelenikr.pia.web.servlet.spring.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet handling user logout requests.
 *
 * @author Roman Zelenik
 */
@WebServlet("/logout")
public class LogoutController extends AbstractController {

//    private static final String ERROR_CLIENT_SESSION = "errorClient";
//    private static final String ERROR_CLIENT_ATTRIBUTE = "errorClient";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log(auth.toString());
            new SecurityContextLogoutHandler().logout(req, resp, auth);
        }

//        Object error = req.getAttribute(ERROR_CLIENT_ATTRIBUTE);
//        if (error == null) {
//            req.getSession().removeAttribute(ERROR_CLIENT_SESSION);
//        } else {
//            req.getSession().setAttribute(ERROR_CLIENT_SESSION, error);
//        }

        resp.sendRedirect("login?logout");
    }

    @Override
    protected String getDefaultTemplatePath() {
        return null;
    }

    @Override
    protected String getViewUrl() {
        return null;
    }
}
