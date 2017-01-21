package org.zelenikr.pia.web.servlet.spring.view.client;

import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.web.servlet.spring.view.AbstractView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roman Zelenik
 */
public abstract class AbstractClientView extends AbstractView {

    protected static final String COPY_PARAMETERS_ATTRIBUTE = "copyParams";
    private static final String AUTHENTICATED_CLIENT_SESSION = "authenticatedClient";

    protected Client getAuthenticatedClient(HttpServletRequest request) {
        return (Client) request.getSession().getAttribute(AUTHENTICATED_CLIENT_SESSION);
    }

}
