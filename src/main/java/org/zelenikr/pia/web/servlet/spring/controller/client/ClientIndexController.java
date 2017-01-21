package org.zelenikr.pia.web.servlet.spring.controller.client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Sends redirect response to 'client index' url.
 *
 * @author Roman Zelenik
 */
@WebServlet("/client")
public class ClientIndexController extends AbstractClientController {

    private static final String INDEX_URL = "client/payment-order";

    @Override
    protected String getDefaultTemplatePath() {
        return "";
    }

    @Override
    protected String getViewUrl() {
        return "";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(INDEX_URL);
    }
}
