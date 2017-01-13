package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.manager.ClientManager;
import org.zelenikr.pia.web.servlet.spring.controller.AbstractController;

/**
 * @author Roman Zelenik
 */
public class AbstractAdminController extends AbstractController {

    protected static final String VIEW_NAME = "/view/adminView";

    private ClientManager clientManager;

    @Autowired
    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }
}
