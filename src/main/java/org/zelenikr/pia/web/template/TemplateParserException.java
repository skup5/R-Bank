package org.zelenikr.pia.web.template;

/**
 * Thrown when Template parsing fails.
 *
 * @author Roman Zelenik
 */
public class TemplateParserException extends Exception {

    public TemplateParserException(String msg) {
        super(msg);
    }

    public TemplateParserException(Throwable e) {
        super(e);
    }
}
