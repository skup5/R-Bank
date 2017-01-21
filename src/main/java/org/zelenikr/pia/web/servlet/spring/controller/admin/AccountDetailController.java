package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.CreditCard;
import org.zelenikr.pia.domain.Currency;
import org.zelenikr.pia.manager.BankAccountManager;
import org.zelenikr.pia.manager.CreditCardManager;
import org.zelenikr.pia.manager.CurrencyManager;
import org.zelenikr.pia.validation.exception.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Servlet handling client's account changes request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/admin/account-detail")
public class AccountDetailController extends AbstractAdminController {

    private static final String TEMPLATE_PATH = "admin/clientDetail";
    private static final String CLIENT_PARAMETER = "client";
    private static final String CLIENT_ATTRIBUTE = "clientData";
    private static final String CLIENT_INFO_FORM_PARAMETER = "clientInfo";
    private static final String NEW_BANK_ACCOUNT_FORM_PARAMETER = "bankAccount";
    private static final String ACTION_PARAMETER = "action";
    private static final String ACTION_DELETE_BANK_ACCOUNT = "delete-bank-account";
    private static final String BANK_ACCOUNT_PARAMETER = "bank-account";

    private BankAccountManager bankAccountManager;
    private CreditCardManager creditCardManager;
    private CurrencyManager currencyManager;

    @Autowired
    public void setBankAccountManager(BankAccountManager bankAccountManager) {
        this.bankAccountManager = bankAccountManager;
    }

    @Autowired
    public void setCreditCardManager(CreditCardManager creditCardManager) {
        this.creditCardManager = creditCardManager;
    }

    @Autowired
    public void setCurrencyManager(CurrencyManager currencyManager) {
        this.currencyManager = currencyManager;
    }

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
        req.setAttribute(CURRENCIES_ATTRIBUTE, currencyManager.getAvailableCurrencies());
        String err = null;
        String success = null;
        String action = req.getParameter(ACTION_PARAMETER);
        String clientId = req.getParameter(CLIENT_PARAMETER);


        if (StringUtils.isNumeric(clientId)) {
            long clientIdNumber = Long.parseLong(clientId);

            try {
                if (action != null) {
                    success = doAction(req, action);
                }
            } catch (ClientValidationException | BankAccountValidationException | RuntimeException e) {
                err = e.getLocalizedMessage();
            }
            Client client = clientManager.loadDetail(clientIdNumber);
            req.getSession().setAttribute(CLIENT_ATTRIBUTE, client);
            req.setAttribute(CLIENT_ATTRIBUTE, client);
        } else {
            err = "Invalid client ID.";
        }

        if (err != null) {
            errorDispatch(err, req, resp);
        } else if (success != null) {
            successDispatch(success, req, resp);
        } else {
            dispatch(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doPost()");
        req.setAttribute(CURRENCIES_ATTRIBUTE, currencyManager.getAvailableCurrencies());
        String clientId = req.getParameter(CLIENT_PARAMETER);
        String clientInfoForm = req.getParameter(CLIENT_INFO_FORM_PARAMETER);
        String bankAccountForm = req.getParameter(NEW_BANK_ACCOUNT_FORM_PARAMETER);
        Client client = (Client) req.getSession().getAttribute(CLIENT_ATTRIBUTE);

        if (client != null && clientId.equals(client.getId().toString())) {
            if (clientInfoForm != null) {
                doClientInfoUpdate(req, resp);
                return;
            } else if (bankAccountForm != null) {
                doCreateBankAccount(req, resp);
                return;
            }
        }
//        else {
//            req.setAttribute(CLIENT_ATTRIBUTE, client);
//            errorDispatch("Client's ID is not same.", req, resp);
        resp.sendRedirect(req.getRequestURL().append(req.getQueryString()).toString());
//        }
    }

    /**
     * @param req
     * @param action
     * @return info message if action was successful or null if it's unknown action
     */
    private String doAction(HttpServletRequest req, String action) throws ClientValidationException, BankAccountValidationException {
        if (action.equals(ACTION_DELETE_BANK_ACCOUNT)) {
            return doDeleteBankAccount(req);
        }
        return null;
    }

    private String doDeleteBankAccount(HttpServletRequest req) throws ClientValidationException, BankAccountValidationException {
        String accountNumber = req.getParameter(BANK_ACCOUNT_PARAMETER);
        if (!StringUtils.isNumeric(accountNumber)) {
            throw new IllegalArgumentException("Invalid bank account number.");
        }

        Client client = (Client) req.getSession().getAttribute(CLIENT_ATTRIBUTE);
        clientManager.removeBankAccount(client, accountNumber);
        return "Client's bank account was successfully removed.";
    }

    private void doClientInfoUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(NAME_PARAMETER),
                surname = req.getParameter(SURNAME_PARAMETER),
                personalIdNo = req.getParameter(PERSONAL_NO_PARAMETER),
                street = req.getParameter(STREET_PARAMETER),
                buildNo = req.getParameter(BUILD_NO_PARAMETER),
                city = req.getParameter(CITY_PARAMETER),
                zip = req.getParameter(ZIP_CODE_PARAMETER),
                email = req.getParameter(EMAIL_PARAMETER),
                phone = req.getParameter(PHONE_NO_PARAMETER);
        Integer houseNoInt = null;
        Client client = (Client) req.getSession().getAttribute(CLIENT_ATTRIBUTE);
        client.setName(name);
        client.setSurname(surname);
        client.setPersonalIdNumber(personalIdNo);
        client.setEmail(email);
        client.setPhoneNumber(phone);
        client.getAddress().setStreet(street);
        client.getAddress().setCity(city);
        client.getAddress().setZipCode(zip);

        if (!StringUtils.isBlank(buildNo)) {
            if (StringUtils.isNumeric(buildNo)) {
                try {
                    houseNoInt = Integer.parseInt(buildNo);
                } catch (NumberFormatException e) {
                    req.setAttribute(CLIENT_ATTRIBUTE, client);
                    errorDispatch("House number is invalid.", req, resp);
                    return;
                }
            } else {
                req.setAttribute(CLIENT_ATTRIBUTE, client);
                errorDispatch("House number is invalid.", req, resp);
                return;
            }
        }

        client.getAddress().setHouseNumber(houseNoInt);
        try {
            clientManager.save(client);
            req.setAttribute(CLIENT_ATTRIBUTE, client);
            successDispatch("Client's account was successfully updated.", req, resp);
        } catch (ClientValidationException | PersonValidationException e) {
            req.setAttribute(CLIENT_ATTRIBUTE, client);
            errorDispatch(e.getLocalizedMessage(), req, resp);
        }
    }

    private void doCreateBankAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cardNo = req.getParameter(CARD_NO_PARAMETER),
                cardPin = req.getParameter(CARD_PIN_PARAMETER),
                bankAccountNo = req.getParameter(BANK_ACCOUNT_NO_PARAMETER),
                currencyType = req.getParameter(CURRENCY_TYPE_PARAMETER);

        Client client = (Client) req.getSession().getAttribute(CLIENT_ATTRIBUTE);

        req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);
        req.setAttribute(CLIENT_ATTRIBUTE, client);

        Integer cardPinInt = null;
        if (StringUtils.isNumeric(cardPin)) {
            try {
                cardPinInt = Integer.parseInt(cardPin);
            } catch (NumberFormatException e) {
                errorDispatch("Credit card pin is invalid.", req, resp);
                return;
            }
        } else {
            errorDispatch("Credit card pin is invalid.", req, resp);
            return;
        }

        Currency currency = null;
        try {
            currency = Currency.valueOf(currencyType);
        } catch (IllegalArgumentException e) {
            errorDispatch("Unknown currency.", req, resp);
            return;
        }

        CreditCard creditCard = new CreditCard(cardNo, cardPinInt);
        try {
            creditCardManager.create(creditCard);
        } catch (CreditCardValidationException e) {
            errorDispatch(e.getLocalizedMessage(), req, resp);
            return;
        }

        BankAccount bankAccount = new BankAccount(bankAccountNo, BigDecimal.ZERO, currency);
        try {
            bankAccountManager.create(bankAccount, creditCard);
        } catch (BankAccountValidationException e) {
            creditCardManager.delete(creditCard);
            errorDispatch(e.getLocalizedMessage(), req, resp);
            return;
        }

        try {
            clientManager.addBankAccount(client, bankAccount);
        } catch (ClientValidationException e) {
            bankAccountManager.delete(bankAccount);
            errorDispatch(e.getLocalizedMessage(), req, resp);
            return;
        }

        req.removeAttribute(COPY_PARAMETERS_ATTRIBUTE);
        successDispatch("Client's new bank account successfully created.", req, resp);
    }
}
