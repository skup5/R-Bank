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
     */
    String getHTMLCode(ServletContext context, String templateName, Map<String, Object> variables) throws IOException;

    /**
     * Writes html into the writer
     *
     * @param context
     * @param templateName
     * @param variables
     * @param writer
     * @throws IOException
     */
    void renderTemplate(ServletContext context, String templateName, Map<String, Object> variables, Writer writer) throws IOException;

    /**
     *
     * @param sharedVariables
     */
    void setSharedVariables(Map<String, Object> sharedVariables);
}
