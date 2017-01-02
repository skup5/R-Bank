package org.zelenikr.pia.web.template;

import de.neuland.jade4j.template.ClasspathTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;
import de.neuland.jade4j.template.ReaderTemplateLoader;
import de.neuland.jade4j.template.TemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
public class JadeRender implements ITemplateRender {

    private final JadeRenderConfiguration config;

    @Autowired
    public JadeRender(JadeRenderConfiguration configuration) {
        this.config = configuration;
    }

    @Override
    public String getHTMLCode(ServletContext context, String templateName, Map<String, Object> variables) throws IOException {
        JadeTemplate template = config.getTemplate(templateName, context);
        if (variables == null)
            variables = Collections.EMPTY_MAP;
        return config.renderTemplate(template, variables);
    }

    @Override
    public void renderTemplate(ServletContext context, String templateName, Map<String, Object> variables, Writer writer) throws IOException {
        JadeTemplate template = config.getTemplate(templateName, context);
        if (variables == null)
            variables = Collections.EMPTY_MAP;
        config.renderTemplate(template, variables, writer);
    }

    @Override
    public void setSharedVariables(Map<String, Object> sharedVariables) {
        this.config.setSharedVariables(sharedVariables);
    }
}
