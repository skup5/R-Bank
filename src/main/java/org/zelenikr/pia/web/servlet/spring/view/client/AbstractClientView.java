package org.zelenikr.pia.web.servlet.spring.view.client;

import org.zelenikr.pia.web.servlet.spring.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract view for all client views
 *
 * @author Roman Zelenik
 */
public abstract class AbstractClientView extends AbstractView {

    protected static final String COPY_PARAMETERS_ATTRIBUTE = "copyParams";

    /**
     * Creates template variables with basic values from request.
     */
    protected final Map<String, Object> initVariables(HttpServletRequest request) {
        Map<String, Object> variables = new HashMap<>();
        int timeout = getAuthenticationTimeout(request);

        variables.put(DISPLAY_NAME_SESSION, getDisplayName(request));
        variables.put(AUTHENTICATION_TIMEOUT_SESSION, timeout);
        variables.put(SUCCESS_ATTRIBUTE, request.getAttribute(SUCCESS_ATTRIBUTE));
        variables.put(ERROR_ATTRIBUTE, request.getAttribute(ERROR_ATTRIBUTE));
        return variables;
    }
}
