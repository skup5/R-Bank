package org.zelenikr.pia.web.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Interface for templates rendering.
 *
 * @author Roman Zelenik
 */
public interface TemplateRender {

    /**
     * Returns html code filled by variables.
     *
     * @param templateName relative path
     * @param variables variables of template
     * @return
     * @throws IOException
     * @throws TemplateParserException
     */
    String getHTMLCode(String templateName, Map<String, Object> variables) throws IOException, TemplateParserException;

    /**
     * Writes html into the writer.
     *
     * @param templateName relative path
     * @param variables variables of template
     * @param writer
     * @throws IOException
     * @throws TemplateParserException
     */
    void renderTemplate(String templateName, Map<String, Object> variables, Writer writer) throws IOException, TemplateParserException;

    /**
     * Shared variables will be inserted into the every template.
     *
     * @param sharedVariables
     */
    void setSharedVariables(Map<String, Object> sharedVariables);
}
