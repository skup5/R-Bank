package org.zelenikr.pia.web.servlet.spring.view;

import org.zelenikr.pia.web.servlet.spring.TemplateServlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roman Zelenik
 */
public abstract class AbstractView extends TemplateServlet{

    protected static final String TEMPLATE_ATTRIBUTE = "templateName";
    protected static final String DISPLAY_NAME_PARAMETER = "displayName";
    protected static final String DISPLAY_NAME_URL_PARAMETER = "displayNameUrl";

    /**
     * The name of the logged in user or null, if the user isn't logged in.
     *
     * @return display name value from session or null
     */
    protected String getDisplayName(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(DISPLAY_NAME_PARAMETER);
    }

    /**
     * Url of user home page.
     *
     * @param request
     * @return display name url value from session or null
     */
    protected String getDisplayNameUrl(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(DISPLAY_NAME_URL_PARAMETER);
    }
}