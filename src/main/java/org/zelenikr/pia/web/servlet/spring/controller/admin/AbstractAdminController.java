package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.manager.ClientManager;
import org.zelenikr.pia.web.servlet.spring.controller.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract controller for all administration controllers
 *
 * @author Roman Zelenik
 */
public abstract class AbstractAdminController extends AbstractController {

    protected static final String VIEW_NAME = "/view/adminView";

    protected ClientManager clientManager;

    @Autowired
    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    /**
     * Page you want render from View. Expect not-null return value.
     *
     * @return default template path for View
     */
    protected abstract String getTemplatePath();

    /**
     * Forward with default template path.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(getTemplatePath(), req, resp);
    }

    /**
     * Forward with specific template path.
     *
     * @param templatePath
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void dispatch(String templatePath, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TEMPLATE_ATTRIBUTE, templatePath);
        req.getRequestDispatcher(VIEW_NAME).forward(req, resp);
    }

    /**
     * Forward with default template path and specific success message.
     *
     * @param success
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void successDispatch(String success, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SUCCESS_ATTRIBUTE, success);
        dispatch(req, resp);
    }

    /**
     * Forward with default template path and specific error message.
     *
     * @param err  error message
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        dispatch(req, resp);
    }
}
