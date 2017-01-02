package org.zelenikr.pia.web.servlet.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.web.template.ITemplateRender;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
public abstract class TemplateServlet extends AbstractServlet {

    @Autowired
    private ITemplateRender templateRender;

    protected abstract Map<String, Object> getTemplateVariables();

    protected String getTemplate(String name) throws IOException {
        return templateRender.getHTMLCode(getServletContext(), name, getTemplateVariables());
    }

    protected void renderTemplate(String name, Writer writer) throws IOException {
        templateRender.renderTemplate(getServletContext(), name, getTemplateVariables(), writer);
    }
}
