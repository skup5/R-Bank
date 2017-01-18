package org.zelenikr.pia.web.servlet.spring.controller.client;

import org.zelenikr.pia.domain.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman Zelenik
 */
@WebServlet("/client/payment-order")
public class PaymentOrderController extends AbstractClientController {

    private static final String TEMPLATE_PATH = "client/paymentOrder";

    private static final String BANK_ACCOUNTS_ATTRIBUTE = "bankAccounts";

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");

        Client client = clientManager.loadDetail(getAuthenticatedUser().getId());
        req.setAttribute(BANK_ACCOUNTS_ATTRIBUTE, client.getBankAccounts());
        req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);

        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
