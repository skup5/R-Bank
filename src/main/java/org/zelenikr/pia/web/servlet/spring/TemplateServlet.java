package org.zelenikr.pia.web.servlet.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.web.template.Helpers;
import org.zelenikr.pia.web.template.ITemplateRender;
import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
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

    protected Map<String, Object> emptyVariables(){
        return Collections.emptyMap();
    }

    protected String getTemplate(String name, Map<String, Object> variables) throws IOException, TemplateParserException {
        return templateRender.getHTMLCode(getServletContext(), name, variables);
    }

    protected void renderTemplate(String name, Map<String, Object> variables, Writer writer) throws IOException, TemplateParserException {
        templateRender.renderTemplate(getServletContext(), name, variables, writer);
    }
}
