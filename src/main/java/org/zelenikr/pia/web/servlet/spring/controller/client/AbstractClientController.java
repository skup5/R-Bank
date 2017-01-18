package org.zelenikr.pia.web.servlet.spring.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.manager.ClientManager;
import org.zelenikr.pia.web.servlet.spring.controller.AbstractController;

/**
 * Abstract controller for all client's controllers
 *
 * @author Roman Zelenik
 */
public abstract class AbstractClientController extends AbstractController {

    private static final String VIEW_URL = "/view/clientView";

    protected ClientManager clientManager;

    @Autowired
    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    protected String getViewUrl() {
        return VIEW_URL;
    }
}
