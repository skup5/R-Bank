package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.zelenikr.pia.domain.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman Zelenik
 */
@WebServlet("/admin/account-detail")
public class AccountDetailController extends AbstractAdminController {

    private static final String TEMPLATE_PATH = "admin/clientDetail";
    private static final String CLIENT_PARAMETER = "client";
    private static final String CLIENT_ATTRIBUTE = "clientData";

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
        String err = null;
        String success = null;
        String clientId = req.getParameter(CLIENT_PARAMETER);

        if (StringUtils.isNumeric(clientId)) {
            long clientIdNumber = Long.parseLong(clientId);
            Client client = clientManager.loadDetail(clientIdNumber);
            req.setAttribute(CLIENT_ATTRIBUTE, client);
            dispatch(req, resp);
        } else {
            errorDispatch("Invalid client ID.", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doPost()");
    }
}
