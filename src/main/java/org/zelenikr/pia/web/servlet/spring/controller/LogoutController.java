package org.zelenikr.pia.web.servlet.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.zelenikr.pia.web.auth.LogoutService;

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

    private LogoutService logoutService;

    @Autowired
    public void setLogoutService(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logoutService.logout(req);
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
