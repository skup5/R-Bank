package org.zelenikr.pia.web.servlet.spring.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.*;
import org.zelenikr.pia.manager.PaymentTransactionManager;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;
import org.zelenikr.pia.validation.exception.PaymentTransactionValidationException;
import org.zelenikr.pia.verification.VerificationSettings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet handling client's new one-time payment order and verification payment request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/client/payment-order")
public class NewPaymentOrderController extends AbstractClientController {

    private static final String VIEW_URL = "/view/client/payment-order";
    private static final String
            DEFAULT_TEMPLATE_PATH = "client/paymentOrder",
            VERIFY_TEMPLATE_PATH = "client/verifyOrder",
            SUCCESS_TEMPLATE_PATH = "client/successfulTransaction";

    private static final String PREPARED_TRANSACTION_SESSION = "preparedTransaction";


    //    private static final String BANK_ACCOUNTS_ATTRIBUTE = "bankAccounts";
    private static final String
            REQUIRED_INPUTS_ATTRIBUTE = "requiredInputs",
            PREPARED_TRANSACTION_ATTRIBUTE = "preparedTransaction",
            VERIFICATION_CODE_LENGTH_ATTRIBUTE = "verificationCodeLength",
            VERIFICATION_CODE_TIMEOUT_ATTRIBUTE = "verificationCodeTimeout";

    private static final String
            CREATE_TRANSACTION_ACTION = "pay",
            VERIFY_TRANSACTION_ACTION = "verify";

    /*
     ### Form inputs ###
     */
    private static final String
            DATE_PARAMETER = "inputDate",
            ACCOUNT_NUMBER_PARAMETER = "selectAccount",
            OFFSET_PARAMETER = "inputOffset",
            BANK_CODE_PARAMETER = "inputBankCode",
            AMOUNT_PARAMETER = "inputAmount",
            CONST_SYMBOL_PARAMETER = "inputConstSymbol",
            VARIABLE_SYMBOL_PARAMETER = "inputVarSymbol",
            SPECIFIC_SYMBOL_PARAMETER = "inputSpecSymbol",
            MESSAGE_PARAMETER = "inputMessage";
    private static final String
            VERIFICATION_CODE_PARAMETER = "verificationCode",
            ACTION_PARAMETER = "action";


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
        return DEFAULT_TEMPLATE_PATH;
    }

    @Override
    protected String getViewUrl() {
        return VIEW_URL;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
        req.setAttribute(REQUIRED_INPUTS_ATTRIBUTE, true);
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String formAction = req.getParameter(ACTION_PARAMETER);
        if (formAction == null) {
            // TODO: error
        } else {
            doAction(formAction, req, resp);
        }
    }

    private void doAction(String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (action) {
            case CREATE_TRANSACTION_ACTION:
                doCreateTransaction(request, response);
                break;
            case VERIFY_TRANSACTION_ACTION:
                doVerifyTransaction(request, response);
                break;
            default:
                // TODO: unknown action
                break;
        }
    }

    private void doCreateTransaction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: get form parameters
        String dateStr = req.getParameter(DATE_PARAMETER),
                accountNumber = req.getParameter(ACCOUNT_NUMBER_PARAMETER),
                offset = req.getParameter(OFFSET_PARAMETER),
                bankCode = req.getParameter(BANK_CODE_PARAMETER),
                amountStr = req.getParameter(AMOUNT_PARAMETER),
                constSymbol = req.getParameter(CONST_SYMBOL_PARAMETER),
                varSymbol = req.getParameter(VARIABLE_SYMBOL_PARAMETER),
                specificSymbol = req.getParameter(SPECIFIC_SYMBOL_PARAMETER),
                message = req.getParameter(MESSAGE_PARAMETER);

        req.setAttribute(REQUIRED_INPUTS_ATTRIBUTE, true);
        req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);

        // TODO: validate form parameters
        Date dueDate = null;
        try {
            dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            errorDispatch(e.getLocalizedMessage(), req, resp);
            return;
        }
        BigDecimal amount = null;
        try {
            amount = new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            errorDispatch("Invalid amount format.", req, resp);
            return;
        }
        PaymentTransaction transaction = new PaymentTransaction(
                TransactionType.ONE_TIME_PAYMENT_ORDER,
                dueDate, amount, new OffsetAccount(offset, bankCode),
                constSymbol, varSymbol, specificSymbol, message
        );

        // TODO: check - is offset account our bank account ?

        try {
            transactionManager.preparePayment(transaction, getAuthenticatedClient(req), accountNumber);
        } catch (PaymentTransactionValidationException | OffsetAccountValidationException | BankAccountValidationException e) {
            errorDispatch(e.getLocalizedMessage(), req, resp);
            return;
        }

//        req.setAttribute(PREPARED_TRANSACTION_ATTRIBUTE, transaction);
//        req.getRequestDispatcher("/controller/client/verify-payment-order").forward(req, resp);

        /*#####  FROM VerifyPaymentOrder  #####*/

        //        if (req.getParameter(CANCEL_TRANSACTION_PARAMETER) != null) {
//            transactionManager.cancelPayment(transaction.getId());
//            resp.sendRedirect("/client");
//            return;
//        }
        req.getSession().setAttribute(PREPARED_TRANSACTION_SESSION, transaction);
        req.setAttribute(PREPARED_TRANSACTION_ATTRIBUTE, transaction);
        req.setAttribute(VERIFICATION_CODE_LENGTH_ATTRIBUTE, verificationSettings.getCodeLength());
        req.setAttribute(VERIFICATION_CODE_TIMEOUT_ATTRIBUTE, verificationSettings.getCodeTimeout());
        dispatch(VERIFY_TEMPLATE_PATH, req, resp);
    }

    private void doVerifyTransaction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaymentTransaction transaction = (PaymentTransaction) req.getSession().getAttribute(PREPARED_TRANSACTION_SESSION);
        String code = req.getParameter(VERIFICATION_CODE_PARAMETER);
        try {
            if (transactionManager.verifyPayment(transaction.getId(), code)) {
                req.getSession().removeAttribute(PREPARED_TRANSACTION_SESSION);
                dispatch(SUCCESS_TEMPLATE_PATH, req, resp);
            } else {
                // wrong code
//                req.setAttribute(ERROR_ATTRIBUTE, "Invalid verification");
//                req.setAttribute(PREPARED_TRANSACTION_ATTRIBUTE, transaction);
//                req.getRequestDispatcher("/client/payment-order").forward(req, resp);
                transactionManager.cancelPayment(transaction.getId());

                errorDispatch("Invalid verification code", req, resp);
            }
        } catch (BankAccountValidationException e) {
            errorDispatch(e.getLocalizedMessage(), req, resp);
        }
    }
}
