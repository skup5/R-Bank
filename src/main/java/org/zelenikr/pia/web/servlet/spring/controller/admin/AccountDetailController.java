package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.validation.exception.ClientValidationException;
import org.zelenikr.pia.validation.exception.PersonValidationException;
import org.zelenikr.pia.validation.exception.UserValidationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman Zelenik
 */
@WebServlet("/admin/account-detail")
public class AccountDetailController extends AbstractAdminController {

    private static final String TEMPLATE_PATH = "admin/clientDetail";
    private static final String CLIENT_PARAMETER = "client";
    private static final String CLIENT_ATTRIBUTE = "clientData";
    private static final String CLIENT_INFO_FORM_PARAMETER = "clientInfo";

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
        String err = null;
        String success = null;
        String clientId = req.getParameter(CLIENT_PARAMETER);

        if (StringUtils.isNumeric(clientId)) {
            long clientIdNumber = Long.parseLong(clientId);
            Client client = clientManager.loadDetail(clientIdNumber);
            req.getSession().setAttribute(CLIENT_ATTRIBUTE, client);
            req.setAttribute(CLIENT_ATTRIBUTE, client);
            dispatch(req, resp);
        } else {
            errorDispatch("Invalid client ID.", req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doPost()");
        String clientId = req.getParameter(CLIENT_PARAMETER);
        String clientInfoForm = req.getParameter(CLIENT_INFO_FORM_PARAMETER);
        Client client = (Client) req.getSession().getAttribute(CLIENT_ATTRIBUTE);

        if (client != null && clientId.equals(client.getId().toString())) {
            if(clientInfoForm != null){
                    doClientInfoUpdate(req,resp);
                    return;
            }
//            else{
//
//            }
        }
//        else {
//            req.setAttribute(CLIENT_ATTRIBUTE, client);
//            errorDispatch("Client's ID is not same.", req, resp);
            resp.sendRedirect(req.getRequestURL().append(req.getQueryString()).toString());
//        }
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
        } catch (ClientValidationException | UserValidationException | PersonValidationException e) {
            req.setAttribute(CLIENT_ATTRIBUTE, client);
            errorDispatch(e.getLocalizedMessage(), req, resp);
        }
    }
}
