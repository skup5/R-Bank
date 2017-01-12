package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.manager.ClientManager;
import org.zelenikr.pia.web.servlet.spring.AbstractServlet;

/**
 * @author Roman Zelenik
 */
public class AbstractAdminController extends AbstractServlet {

    protected static final String TEMPLATE_ATTRIBUTE = "templateName";
    protected static final String VIEW_NAME = "/view/adminView";

    private ClientManager clientManager;

    //@Autowired
    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }
}
