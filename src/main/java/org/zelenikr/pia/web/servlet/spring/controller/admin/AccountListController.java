package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet handling client accounts list request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/admin/account-list")
public class AccountListController extends AbstractAdminController {

    private static final String TEMPLATE_PATH = "admin/accountList";
    private static final String CLIENTS_ATTRIBUTE = "clients";
    private static final String ACTION_PARAMETER = "action";
    private static final String ACTION_DELETE = "delete";
    private static final String CLIENT_PARAMETER = "client";

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
        String err = null;
        String success = null;
        String action = req.getParameter(ACTION_PARAMETER);

        try {
            if (action != null) {
                success = doAction(req, action);
            }
        } catch (RuntimeException e) {
            err = e.getLocalizedMessage();
        }

        req.setAttribute(CLIENTS_ATTRIBUTE, clientManager.getClients());

        if (err != null) {
            errorDispatch(err, req, resp);
        } else if (success != null) {
            successDispatch(success, req, resp);
        } else {
            dispatch(req, resp);
        }
    }

    /**
     * @param req
     * @param action
     * @return info message if action was successful or null if it's unknown action
     */
    private String doAction(HttpServletRequest req, String action) {
        if (action.equals(ACTION_DELETE)) {
            return doDelete(req);
        }
        return null;
    }

    private String doDelete(HttpServletRequest req) {
        String clientID = req.getParameter(CLIENT_PARAMETER);
        if (!StringUtils.isNumeric(clientID)) {
            throw new IllegalArgumentException("Invalid client ID.");
        }
        if (clientManager.delete(Long.parseLong(clientID))) {
            return "Client's account was successfully removed.";
        } else {
            throw new IllegalArgumentException("Client's account not found.");
        }
    }

}
