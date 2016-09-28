package org.danekja.edu.pia.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danekja.edu.pia.web.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Servlet handling user login requests
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@WebServlet("/login")
public class Login extends AbstractServlet {

    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";

    private static final String ERR_ATTRIBUTE = "err";

    private AuthenticationService authService;

    @Autowired
    public void setAuthService(AuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);

        boolean authenticated = authService.authenticate(req.getSession(), username, password);
        if(authenticated) {
            resp.sendRedirect("secret/vip");
        } else {
            req.setAttribute(ERR_ATTRIBUTE, "Invalid credentials!");
            req.getRequestDispatcher("/").forward(req, resp);
        }
    }
}
