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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
public abstract class TemplateServlet extends AbstractServlet {

    protected static final String DISPLAY_NAME_PARAMETER = "displayName";

    @Autowired
    private ITemplateRender templateRender;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Map<String, Object> defaults = new HashMap<String, Object>();
        defaults.put("date", new Helpers.DateHelper());
        this.templateRender.setSharedVariables(defaults);
    }

    protected Map<String, Object> emptyVariables(){
        return Collections.emptyMap();
    }

    protected String getTemplate(String name, Map<String, Object> variables) throws IOException, TemplateParserException {
        return templateRender.getHTMLCode(getServletContext(), name, variables);
    }

    protected void renderTemplate(String name, Map<String, Object> variables, Writer writer) throws IOException, TemplateParserException {
        templateRender.renderTemplate(getServletContext(), name, variables, writer);
    }

    /**
     * Request attributes to template variables
     *
     * @param request
     * @return
     */
    protected Map<String, Object> createVariablesFromAttributes(HttpServletRequest request) {
        String name;
        Map<String, Object> variables = new HashedMap();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            name = attributeNames.nextElement();
            variables.put(name, request.getAttribute(name));
        }
        return variables;
    }
}
