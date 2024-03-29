package org.zelenikr.pia.web.servlet.spring;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Date: 27.11.15
 *
 * @author Jakub Danek
 */
public abstract class AbstractServlet extends HttpServlet {

    protected static final String ERROR_ATTRIBUTE = "err";
    protected static final String SUCCESS_ATTRIBUTE = "success";
    protected static final String WARNING_ATTRIBUTE = "warning";

    protected AutowireCapableBeanFactory ctx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(getServletContext());
        ctx = context.getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
    }
}