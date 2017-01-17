package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.Address;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.CreditCard;
import org.zelenikr.pia.manager.AddressManager;
import org.zelenikr.pia.manager.BankAccountManager;
import org.zelenikr.pia.manager.CreditCardManager;
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

    private static final String NAME_PARAMETER = "inputName";
    private static final String SURNAME_PARAMETER = "inputSurname";
    private static final String PERSONAL_NO_PARAMETER = "inputPersonalNo";
    private static final String STREET_PARAMETER = "inputStreet";
    private static final String BUILD_NO_PARAMETER = "inputBuildNo";
    private static final String CITY_PARAMETER = "inputCity";
    private static final String ZIP_CODE_PARAMETER = "inputZipCode";
    private static final String EMAIL_PARAMETER = "inputEmail";
    private static final String PHONE_NO_PARAMETER = "inputPhoneNumber";
    private static final String CARD_NO_PARAMETER = "inputCardNo";
    private static final String CARD_PIN_PARAMETER = "inputCardPinNo";
    private static final String BANK_ACCOUNT_NO_PARAMETER = "inputBankAccountNo";
    private static final String ROBOT_CHECK = "robotCheck";

    private AddressManager addressManager;
    private BankAccountManager bankAccountManager;
    private CreditCardManager creditCardManager;

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

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                robotCheck = req.getParameter(ROBOT_CHECK);
        Integer houseNoInt = null, cardPinInt = null;

        req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);

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
            errorDispatch("Credit card is invalid.", req, resp);
            return;
        }

        CreditCard creditCard = new CreditCard(cardNo, cardPinInt);
        try {
            creditCardManager.create(creditCard);
        } catch (CreditCardValidationException e) {
           // clientManager.delete(client);
            errorDispatch(e.getLocalizedMessage(), req, resp);
            return;
        }

        BankAccount bankAccount = new BankAccount(bankAccountNo, BigDecimal.ZERO);
        try {
//            bankAccountManager.create(bankAccount, creditCard, client);
            bankAccountManager.create(bankAccount, creditCard, null);
        } catch (BankAccountValidationException e) {
            creditCardManager.delete(creditCard);
            errorDispatch(e.getLocalizedMessage(), req, resp);
            return;
        }
        Address clientAddress = new Address(houseNoInt, street, city, zip);
        Client client = new Client(name, surname, personalIdNo, phone, email);
        try {
            addressManager.create(clientAddress);
//            clientManager.register(client, clientAddress);
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
