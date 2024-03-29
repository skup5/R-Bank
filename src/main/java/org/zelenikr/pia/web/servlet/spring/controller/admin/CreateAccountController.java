package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.*;
import org.zelenikr.pia.manager.AddressManager;
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
import java.util.Enumeration;

/**
 * Servlet handling new bank client registration request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/admin/create-account")
public class CreateAccountController extends AbstractAdminController {

    private static final String TEMPLATE_PATH = "admin/createAccount";

    private static final String ROBOT_CHECK = "robotCheck";

    private AddressManager addressManager;
    private BankAccountManager bankAccountManager;
    private CreditCardManager creditCardManager;
    private CurrencyManager currencyManager;

    @Autowired
    public void setAddressManager(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

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
        req.setAttribute(CURRENCIES_ATTRIBUTE, currencyManager.getAvailableCurrencies());
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doPost()");
        //printParams(req);
        String name = req.getParameter(NAME_PARAMETER),
                surname = req.getParameter(SURNAME_PARAMETER),
                personalIdNo = req.getParameter(PERSONAL_NO_PARAMETER),
                street = req.getParameter(STREET_PARAMETER),
                buildNo = req.getParameter(BUILD_NO_PARAMETER),
                city = req.getParameter(CITY_PARAMETER),
                zip = req.getParameter(ZIP_CODE_PARAMETER),
                email = req.getParameter(EMAIL_PARAMETER),
                phone = req.getParameter(PHONE_NO_PARAMETER),
                cardNo = req.getParameter(CARD_NO_PARAMETER),
                cardPin = req.getParameter(CARD_PIN_PARAMETER),
                bankAccountNo = req.getParameter(BANK_ACCOUNT_NO_PARAMETER),
                currencyType = req.getParameter(CURRENCY_TYPE_PARAMETER),
                robotCheck = req.getParameter(ROBOT_CHECK);
        Integer houseNoInt = null, cardPinInt = null;

        req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);
        req.setAttribute(CURRENCIES_ATTRIBUTE, currencyManager.getAvailableCurrencies());

        if (robotCheck == null) {
            errorDispatch("Confirm that you're not a robot.", req, resp);
            return;
        }

        if (!StringUtils.isBlank(buildNo)) {
            if (StringUtils.isNumeric(buildNo)) {
                try {
                    houseNoInt = Integer.parseInt(buildNo);
                } catch (NumberFormatException e) {
                    errorDispatch("House number is invalid.", req, resp);
                    return;
                }
            } else {
                errorDispatch("House number is invalid.", req, resp);
                return;
            }
        }

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

        Address clientAddress = new Address(houseNoInt, street, city, zip);
        Client client = new Client(name, surname, personalIdNo, phone, email);
        try {
            addressManager.create(clientAddress);
            clientManager.register(client, clientAddress, bankAccount);
        } catch (UserValidationException | PersonValidationException | ClientValidationException e) {
            addressManager.delete(clientAddress);
            bankAccountManager.delete(bankAccount);
            errorDispatch(e.getLocalizedMessage(), req, resp);
            return;
        }


//        for(Client c : clientManager.getClients()){
//            try {
//                c = clientManager.loadDetail(c)    ;
//            } catch (ClientValidationException e) {
//                e.printStackTrace();
//            }
//            System.out.println(c);
//            System.out.println("--Bank accounts--");
//            for(BankAccount ba : c.getBankAccounts()){
//                System.out.println(ba);
//            }
//            System.out.println("----------------------------");
//        }

//        clientManager.delete(client);
//        for(Client c : clientManager.getClients()) System.out.println("* "+c);

        req.removeAttribute(COPY_PARAMETERS_ATTRIBUTE);
        successDispatch("New client's bank account successfully created.", req, resp);
    }

    private void printParams(HttpServletRequest req) {
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            System.out.println(parameterNames.nextElement());
        }
    }

}
