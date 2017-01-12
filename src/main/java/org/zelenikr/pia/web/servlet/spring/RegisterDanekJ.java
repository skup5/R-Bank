package org.zelenikr.pia.web.servlet.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.RoleType;
import org.zelenikr.pia.domain.User;
import org.zelenikr.pia.manager.UserManager;
import org.zelenikr.pia.validation.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Servlet handling user registration requests.
 * <p>
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@WebServlet("/register")
public class RegisterDanekJ extends AbstractServlet {

    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String CONFIRM_PWD_PARAMETER = "confirmPwd";

    private static final String ERROR_ATTRIBUTE = "err";

    private UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String confirmPwd = req.getParameter(CONFIRM_PWD_PARAMETER);

        //TODO: fill missing info from form
        String name = "name of user";

        if (!Objects.equals(password, confirmPwd)) {
            errorDispatch("The password and confirm password fields do not match!", req, resp);
            return;
        }

        try {
            ArrayList<RoleType> roles = new ArrayList();
            roles.add(RoleType.ROLE_ADMIN);
//            roles.add(RoleType.ROLE_CLIENT);
            userManager.register(new User(username, password), roles);
//            userManager.register(userManager.create());
            //TODO: not perfect, user should get a message registration was successful!
            resp.sendRedirect("");
        } catch (ValidationException e) {
            errorDispatch(e.getMessage(), req, resp);
        }
    }

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

}