package org.zelenikr.pia.web.template;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
public interface ITemplateRender {

    /**
     * Returns html code
     *
     * @param templateName
     * @param variables
     * @return
     * @throws IOException
     * @throws TemplateParserException
     */
    String getHTMLCode(ServletContext context, String templateName, Map<String, Object> variables) throws IOException, TemplateParserException;

    /**
     * Writes html into the writer
     *
     * @param context
     * @param templateName
     * @param variables
     * @param writer
     * @throws IOException
     * @throws TemplateParserException
     */
    void renderTemplate(ServletContext context, String templateName, Map<String, Object> variables, Writer writer) throws IOException, TemplateParserException;

    /**
     *
     * @param sharedVariables
     */
    void setSharedVariables(Map<String, Object> sharedVariables);
}
