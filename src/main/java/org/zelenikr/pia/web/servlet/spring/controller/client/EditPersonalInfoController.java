package org.zelenikr.pia.web.servlet.spring.controller.client;

import org.apache.commons.lang3.StringUtils;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.validation.exception.ClientValidationException;
import org.zelenikr.pia.validation.exception.PersonValidationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet handling client's edit personal info request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/client/settings-personal")
public class EditPersonalInfoController extends AbstractClientController {

    private static final String VIEW_URL = "/view/client/settings";
    private static final String DEFAULT_TEMPLATE_PATH = "client/accountSettings";

    private static final String
            CLIENT_ATTRIBUTE = "client",
            ADDRESS_ATTRIBUTE = "address";

    /*
     ###  Form inputs  ###
     */
    private static final String
            STREET_PARAMETER = "inputStreet",
            BUILD_NO_PARAMETER = "inputBuildNo",
            CITY_PARAMETER = "inputCity",
            ZIP_CODE_PARAMETER = "inputZipCode",
            EMAIL_PARAMETER = "inputEmail",
            PHONE_NO_PARAMETER = "inputPhoneNumber";

    @Override
    public String getViewUrl() {
        return VIEW_URL;
    }

    @Override
    public String getDefaultTemplatePath() {
        return DEFAULT_TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(CLIENT_ATTRIBUTE, getAuthenticatedClient(req));
        req.setAttribute(ADDRESS_ATTRIBUTE, getAuthenticatedClient(req).getAddress());
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String street = req.getParameter(STREET_PARAMETER),
                buildNo = req.getParameter(BUILD_NO_PARAMETER),
                city = req.getParameter(CITY_PARAMETER),
                zip = req.getParameter(ZIP_CODE_PARAMETER),
                email = req.getParameter(EMAIL_PARAMETER),
                phone = req.getParameter(PHONE_NO_PARAMETER);
        Integer houseNoInt = null;
        Client client = getAuthenticatedClient(req);

        req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);

        if (!StringUtils.isBlank(buildNo)) {
            if (StringUtils.isNumeric(buildNo)) {
                try {
                    houseNoInt = Integer.parseInt(buildNo);
                } catch (NumberFormatException e) {
                    req.setAttribute(ERROR_ATTRIBUTE, "House number is invalid.");
                    doGet(req, resp);
                    return;
                }
            } else {
                req.setAttribute(ERROR_ATTRIBUTE, "House number is invalid.");
                doGet(req, resp);
                return;
            }
        }

        client.setEmail(email);
        client.setPhoneNumber(phone);
        client.getAddress().setStreet(street);
        client.getAddress().setCity(city);
        client.getAddress().setZipCode(zip);
        client.getAddress().setHouseNumber(houseNoInt);

        try {
            clientManager.save(client);
            req.setAttribute(SUCCESS_ATTRIBUTE, "Changes were successfully saved.");
        } catch (ClientValidationException | PersonValidationException e) {
            req.setAttribute(ERROR_ATTRIBUTE, e.getLocalizedMessage());
        }
        doGet(req, resp);
    }
}
