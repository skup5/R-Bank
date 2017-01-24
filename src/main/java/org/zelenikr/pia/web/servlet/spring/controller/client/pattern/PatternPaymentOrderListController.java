package org.zelenikr.pia.web.servlet.spring.controller.client.pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.PatternPaymentOrder;
import org.zelenikr.pia.manager.PatternPaymentOrderManager;
import org.zelenikr.pia.web.servlet.spring.controller.client.AbstractClientController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet handling client's list of payment patterns, edit and delete request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/client/payment-pattern-list")
public class PatternPaymentOrderListController extends AbstractClientController {

    private static final String VIEW_URL = "/view/client/payment-pattern-list";
    private static final String DEFAULT_TEMPLATE_PATH = "client/allPaymentPatterns";

    private static final String
            PATTERNS_LIST_SESSION = "paymentPatterns",
            EDITED_PATTERN_SESSION = "editedPattern";

    private static final String
            PATTERNS_LIST_ATTRIBUTE = "paymentPatterns",
            ACTION_PARAMETER = "action",
            PATTERN_ID_PARAMETER = "patternId",
            PATTERN_NAME_PARAMETER = "patternName";

    private static final String
            DELETE_ACTION = "delete",
            EDIT_ACTION = "edit";


    private PatternPaymentOrderManager patternPaymentOrderManager;


    @Override
    protected String getDefaultTemplatePath() {
        return DEFAULT_TEMPLATE_PATH;
    }

    @Override
    protected String getViewUrl() {
        return VIEW_URL;
    }

    @Autowired
    public void setPatternPaymentOrderManager(PatternPaymentOrderManager patternPaymentOrderManager) {
        this.patternPaymentOrderManager = patternPaymentOrderManager;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PatternPaymentOrder> patterns = patternPaymentOrderManager.findClientPatterns(getAuthenticatedClient(req).getId());
        req.getSession().setAttribute(PATTERNS_LIST_SESSION, patterns);
        req.setAttribute(PATTERNS_LIST_ATTRIBUTE, patterns);
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String formAction = req.getParameter(ACTION_PARAMETER);
        if (formAction == null) {
            req.setAttribute(ERROR_ATTRIBUTE, "Invalid action!");
            doGet(req, resp);
        } else {
            doAction(formAction, req, resp);
        }
    }

    private void doAction(String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (action) {
            case DELETE_ACTION:
                doDeletePattern(request, response);
                break;
            case EDIT_ACTION:
                doEditPattern(request, response);
                break;
            default:
                request.setAttribute(ERROR_ATTRIBUTE, "Invalid action!");
                doGet(request, response);
                break;
        }
    }

    private void doDeletePattern(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter(PATTERN_NAME_PARAMETER);
        if (patternPaymentOrderManager.delete(name, getAuthenticatedClient(request))) {
            request.setAttribute(SUCCESS_ATTRIBUTE,"Pattern was successfully deleted.");
            doGet(request, response);
        } else {
            request.setAttribute(ERROR_ATTRIBUTE,"Cannot delete, pattern was not found.");
            doGet(request, response);
        }
    }

    private void doEditPattern(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idstr = request.getParameter(PATTERN_ID_PARAMETER);
        Long id = null;
        try {
            id = Long.parseLong(idstr);
        } catch (NumberFormatException e) {
            request.setAttribute(ERROR_ATTRIBUTE,"Invalid pattern ID");
            doGet(request, response);
            return;
        }
        List<PatternPaymentOrder> patterns = (List<PatternPaymentOrder>) request.getSession().getAttribute(PATTERNS_LIST_SESSION);
        PatternPaymentOrder editedPattern = null;
        for (PatternPaymentOrder pattern : patterns) {
            if (pattern.getId().compareTo(id) == 0)
                editedPattern = pattern;
        }
        if (editedPattern == null) {
            request.setAttribute(ERROR_ATTRIBUTE,"Invalid pattern ID");
            doGet(request, response);
            return;
        }
        request.getSession().setAttribute(EDITED_PATTERN_SESSION, editedPattern);
        response.sendRedirect("payment-pattern-edit");
    }
}
