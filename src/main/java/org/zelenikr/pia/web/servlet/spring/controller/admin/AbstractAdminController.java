package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.manager.ClientManager;
import org.zelenikr.pia.web.servlet.spring.controller.AbstractController;

/**
 * Abstract controller for all administration controllers
 *
 * @author Roman Zelenik
 */
public abstract class AbstractAdminController extends AbstractController {

    protected static final String VIEW_URL = "/view/adminView";
    protected static final String COPY_PARAMETERS_ATTRIBUTE = "copyParams";
    protected static final String CURRENCIES_ATTRIBUTE = "currencies";

    /*
     ###  Form inputs  ###
     */
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
    protected static final String CURRENCY_TYPE_PARAMETER = "selectCurrency";

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
