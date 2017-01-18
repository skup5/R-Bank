package org.zelenikr.pia.web.servlet.spring.controller;

import org.zelenikr.pia.web.servlet.spring.AbstractServlet;
import org.zelenikr.pia.web.servlet.spring.view.AbstractView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract controller for all controllers cooperating with {@link AbstractView} instances.
 *
 * @author Roman Zelenik
 */
public abstract class AbstractController extends AbstractServlet {

    protected static final String TEMPLATE_ATTRIBUTE = "templateName";

    /**
     * Default page you want render from {@link AbstractView} instance. Expect not-null return value.
     *
     * @return default template path for View
     */
    protected abstract String getDefaultTemplatePath();

    /**
     * Servlet mapping url of {@link AbstractView} instance for forwarding. Expect not-null return value.
     *
     * @return mapping url
     */
    protected abstract String getViewUrl();

    /**
     * Forward with default template path.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(getDefaultTemplatePath(), req, resp);
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
        req.getRequestDispatcher(getViewUrl()).forward(req, resp);
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
