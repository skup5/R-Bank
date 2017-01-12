package org.zelenikr.pia.web.servlet.spring;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.web.servlet.spring.view.AbstractView;
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

}
