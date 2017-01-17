package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.manager.ClientManager;
import org.zelenikr.pia.web.servlet.spring.controller.AbstractController;
import org.zelenikr.pia.web.servlet.spring.view.AbstractView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract controller for all administration controllers
 *
 * @author Roman Zelenik
 */
public abstract class AbstractAdminController extends AbstractController {

    protected static final String VIEW_NAME = "/view/adminView";
    protected static final String COPY_PARAMETERS_ATTRIBUTE = "copyParams";

    /*  Form inputs     */
    protected static final String NAME_PARAMETER = "inputName";
    protected static final String SURNAME_PARAMETER = "inputSurname";
    protected static final String PERSONAL_NO_PARAMETER = "inputPersonalNo";
    protected static final String STREET_PARAMETER = "inputStreet";
    protected static final String BUILD_NO_PARAMETER = "inputBuildNo";
    protected static final String CITY_PARAMETER = "inputCity";
    protected static final String ZIP_CODE_PARAMETER = "inputZipCode";
    protected static final String EMAIL_PARAMETER = "inputEmail";
    protected static final String PHONE_NO_PARAMETER = "inputPhoneNumber";
    protected static final String CARD_NO_PARAMETER = "inputCardNo";
    protected static final String CARD_PIN_PARAMETER = "inputCardPinNo";
    protected static final String BANK_ACCOUNT_NO_PARAMETER = "inputBankAccountNo";

    protected ClientManager clientManager;

    @Autowired
    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    /**
     * Default page you want render from {@link AbstractView} instance. Expect not-null return value.
     *
     * @return default template path for View
     */
    protected abstract String getDefaultTemplatePath();

    /**
     * Forward with default template path.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(getDefaultTemplatePath(), req, resp);
    }

    /**
     * Forward with specific template path.
     *
     * @param templatePath
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void dispatch(String templatePath, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TEMPLATE_ATTRIBUTE, templatePath);
        req.getRequestDispatcher(VIEW_NAME).forward(req, resp);
    }

    /**
     * Forward with default template path and specific success message.
     *
     * @param success
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void successDispatch(String success, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(SUCCESS_ATTRIBUTE, success);
        dispatch(req, resp);
    }

    /**
     * Forward with default template path and specific error message.
     *
     * @param err  error message
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        dispatch(req, resp);
    }
}
