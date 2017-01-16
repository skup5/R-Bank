package org.zelenikr.pia.web.template.jade;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.exceptions.JadeException;
import de.neuland.jade4j.template.JadeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.web.template.TemplateRender;
import org.zelenikr.pia.web.template.TemplateParserException;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

/**
 * Jade implementation of the {@link TemplateRender} interface.
 *
 * @author Roman Zelenik
 */
public class JadeRender implements TemplateRender {

    private final JadeConfiguration config;

    @Autowired
    public JadeRender(JadeConfiguration configuration) {
        this.config = configuration;
    }

    @Override
    public String getHTMLCode(String templateName, Map<String, Object> variables) throws IOException, TemplateParserException {
        try {
            JadeTemplate template = config.getTemplate(templateName);
            if (variables == null)
                variables = Collections.EMPTY_MAP;
            return config.renderTemplate(template, variables);
        } catch (JadeException e) {
            throw new TemplateParserException(e);
        }
    }

    @Override
    public void renderTemplate(String templateName, Map<String, Object> variables, Writer writer) throws IOException, TemplateParserException {
        try {
            JadeTemplate template = config.getTemplate(templateName);
            if (variables == null)
                variables = Collections.EMPTY_MAP;
            config.renderTemplate(template, variables, writer);
        } catch (JadeException e) {
            throw new TemplateParserException(e);
        }
    }

    @Override
    public void setSharedVariables(Map<String, Object> sharedVariables) {
        this.config.setSharedVariables(sharedVariables);
    }
}
