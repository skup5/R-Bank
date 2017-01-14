package org.zelenikr.pia.web.servlet.spring.controller;

import org.zelenikr.pia.web.servlet.spring.AbstractServlet;
import org.zelenikr.pia.web.servlet.spring.view.AbstractView;

/**
 * Abstract controller for all controllers cooperating with {@link AbstractView} instances.
 *
 * @author Roman Zelenik
 */
public class AbstractController extends AbstractServlet {

    protected static final String TEMPLATE_ATTRIBUTE = "templateName";
}
