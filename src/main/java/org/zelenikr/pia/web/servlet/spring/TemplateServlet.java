package org.zelenikr.pia.web.servlet.spring;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.web.template.Helpers;
import org.zelenikr.pia.web.template.ITemplateRender;
import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
public abstract class TemplateServlet extends AbstractServlet {

    protected static final String ERROR_ATTRIBUTE = "err";
    protected static final String TEMPLATE_ATTRIBUTE = "templateName";
    protected static final String DISPLAY_NAME_PARAMETER = "displayName";
    protected static final String DISPLAY_NAME_URL_PARAMETER = "displayNameUrl";

    @Autowired
    private ITemplateRender templateRender;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Map<String, Object> defaults = new HashMap<String, Object>();
        defaults.put("date", new Helpers.DateHelper());
        this.templateRender.setSharedVariables(defaults);
    }

    /**
     * Creates empty variables map.
     *
     * @return
     */
    protected Map<String, Object> emptyVariables() {
        return new HashMap<>();
    }

    protected String getTemplate(String name, Map<String, Object> variables) throws IOException, TemplateParserException {
        return templateRender.getHTMLCode(getServletContext(), name, variables);
    }

    protected void renderTemplate(String name, Map<String, Object> variables, Writer writer) throws IOException, TemplateParserException {
        templateRender.renderTemplate(getServletContext(), name, variables, writer);
    }

    /**
     * Create template variables from request parameters.
     *
     * @param request
     * @return
     */
    protected Map<String, Object> createVariablesFromParameters(HttpServletRequest request) {
        //log("createVariablesFromParameters");
        String name;
        String[] paramArray;
        Map<String, Object> variables = new HashedMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            name = parameterNames.nextElement();
            paramArray = request.getParameterValues(name);
            if (paramArray.length == 1)
                variables.put(name, paramArray[0]);
            else
                variables.put(name, paramArray);
//            System.out.println(name+":"+request.getParameter(name));
        }
        return variables;
    }

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
