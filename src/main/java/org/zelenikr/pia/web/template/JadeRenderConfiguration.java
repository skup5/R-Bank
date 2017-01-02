package org.zelenikr.pia.web.template;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
public class JadeRenderConfiguration {

    private final JadeConfiguration config;
    private JadeFileLoader templateLoader;

    @Autowired
    public JadeRenderConfiguration(JadeFileLoader templateLoader) {
        this.templateLoader = templateLoader;
        this.config = new JadeConfiguration();
        this.config.setTemplateLoader(this.templateLoader);
    }

    public JadeTemplate getTemplate(String template, ServletContext context) throws IOException {
        templateLoader.setContext(context);
        return config.getTemplate(template);
    }

    public String renderTemplate(JadeTemplate template, Map<String, Object> variables) {
        return config.renderTemplate(template, variables);
    }

    public void renderTemplate(JadeTemplate template, Map<String, Object> variables, Writer writer) {
        config.renderTemplate(template, variables, writer);
    }

    public void setCaching(Boolean caching) {
        this.config.setCaching(caching);
    }

    public void setPrettyPrint(Boolean prettyPrint) {
        this.config.setPrettyPrint(prettyPrint);
    }

    public void setSharedVariables(Map<String, Object> sharedVariables) {
        this.config.setSharedVariables(sharedVariables);
    }
}
