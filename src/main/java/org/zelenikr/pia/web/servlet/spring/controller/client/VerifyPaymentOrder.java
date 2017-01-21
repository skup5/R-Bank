package org.zelenikr.pia.web.servlet.spring.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.manager.PaymentTransactionManager;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
import org.zelenikr.pia.verification.VerificationSettings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet handling transaction verification request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/controller/client/verify-payment-order")
public class VerifyPaymentOrder extends AbstractClientController {

    private static final String VIEW_URL = "/view/client/payment-order";
    private static final String TEMPLATE_PATH = "client/verifyOrder";

    private static final String PREPARED_TRANSACTION_SESSION = "preparedTransaction";

    private static final String
            PREPARED_TRANSACTION_ATTRIBUTE = "preparedTransaction",
            VERIFICATION_CODE_LENGTH_ATTRIBUTE = "verificationCodeLength";

    private static final String
            VERIFICATION_CODE_PARAMETER = "verificationCode",
            CANCEL_TRANSACTION_PARAMETER = "cancel";

    private PaymentTransactionManager transactionManager;
    private VerificationSettings verificationSettings;

    @Autowired
    public void setTransactionManager(PaymentTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Autowired
    public void setVerificationSettings(VerificationSettings verificationSettings) {
        this.verificationSettings = verificationSettings;
    }

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected String getViewUrl() {
        return VIEW_URL;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
        PaymentTransaction transaction = (PaymentTransaction) req.getAttribute(PREPARED_TRANSACTION_ATTRIBUTE);
        if (req.getParameter(CANCEL_TRANSACTION_PARAMETER) != null) {
            transactionManager.cancelPayment(transaction.getId());
            resp.sendRedirect("/client");
            return;
        }
        req.getSession().setAttribute(PREPARED_TRANSACTION_SESSION, transaction);
//        Client client = getAuthenticatedClient(req);
        req.setAttribute(VERIFICATION_CODE_LENGTH_ATTRIBUTE, verificationSettings.getCodeLength());
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaymentTransaction transaction = (PaymentTransaction) req.getSession().getAttribute(PREPARED_TRANSACTION_SESSION);
        String code = req.getParameter(VERIFICATION_CODE_PARAMETER);
        try {
            if (transactionManager.verifyPayment(transaction.getId(), code)) {
                req.getSession().removeAttribute(PREPARED_TRANSACTION_SESSION);
                dispatch("client/successfulTransaction", req, resp);
            } else {
                // wrong code
//                req.setAttribute(ERROR_ATTRIBUTE, "Invalid verification");
//                req.setAttribute(PREPARED_TRANSACTION_ATTRIBUTE, transaction);
//                req.getRequestDispatcher("/client/payment-order").forward(req, resp);
                errorDispatch("Invalid verification code", req, resp);
            }
        } catch (BankAccountValidationException e) {
            errorDispatch(e.getLocalizedMessage(), req, resp);
        }
    }
}
