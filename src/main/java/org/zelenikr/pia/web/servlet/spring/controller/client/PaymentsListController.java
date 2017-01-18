package org.zelenikr.pia.web.servlet.spring.controller.client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman Zelenik
 */
@WebServlet("/client/all-payments")
public class PaymentsListController extends AbstractClientController {

    private static final String TEMPLATE_PATH = "client/allPayments";

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
        //TODO: get payment list

        dispatch(req, resp);
    }
}
