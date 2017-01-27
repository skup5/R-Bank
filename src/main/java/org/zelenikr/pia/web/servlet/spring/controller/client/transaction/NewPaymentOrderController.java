package org.zelenikr.pia.web.servlet.spring.controller.client.transaction;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.bankcode.BankCodeManager;
import org.zelenikr.pia.domain.*;
import org.zelenikr.pia.manager.CurrencyManager;
import org.zelenikr.pia.manager.PaymentTransactionManager;
import org.zelenikr.pia.validation.exception.BankAccountValidationException;
import org.zelenikr.pia.validation.exception.OffsetAccountValidationException;
import org.zelenikr.pia.validation.exception.PaymentTransactionValidationException;
import org.zelenikr.pia.verification.VerificationSettings;
import org.zelenikr.pia.web.servlet.spring.controller.client.AbstractClientController;

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

    private static final String
            PREPARED_TRANSACTION_SESSION = "preparedTransaction",
            VERIFICATION_CODE_TIMEOUT_SESSION = "transactionVerificationCodeTimeout";

    private static final String
            BANK_CODES_ATTRIBUTE = "bankCodes",
            CURRENCIES_ATTRIBUTE = "currencies",
            PREPARED_TRANSACTION_ATTRIBUTE = "preparedTransaction",
            VERIFICATION_CODE_LENGTH_ATTRIBUTE = "verificationCodeLength",
            VERIFICATION_CODE_TIMEOUT_ATTRIBUTE = "verificationCodeTimeout";

    private static final String
            CREATE_TRANSACTION_ACTION = "pay",
            VERIFY_TRANSACTION_ACTION = "verify",
            CANCEL_TRANSACTION_ACTION = "cancel";

    /*
     ### Form inputs ###
     */
    private static final String
            DATE_PARAMETER = "inputDate",
            ACCOUNT_NUMBER_PARAMETER = "selectAccount",
            OFFSET_PARAMETER = "inputOffset",
            BANK_CODE_PARAMETER = "inputBankCode",
            AMOUNT_PARAMETER = "inputAmount",
            CURRENCY_NAME_PARAMETER = "selectCurrency",
            CONST_SYMBOL_PARAMETER = "inputConstSymbol",
            VARIABLE_SYMBOL_PARAMETER = "inputVarSymbol",
            SPECIFIC_SYMBOL_PARAMETER = "inputSpecSymbol",
            MESSAGE_PARAMETER = "inputMessage";
    private static final String
            VERIFICATION_CODE_PARAMETER = "verificationCode",
            ACTION_PARAMETER = "action";


    private PaymentTransactionManager transactionManager;
    private VerificationSettings verificationSettings;
    private BankCodeManager bankCodeManager;
    private CurrencyManager currencyManager;

    @Autowired
    public void setTransactionManager(PaymentTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Autowired
    public void setVerificationSettings(VerificationSettings verificationSettings) {
        this.verificationSettings = verificationSettings;
    }

    @Autowired
    public void setBankCodeManager(BankCodeManager bankCodeManager) {
        this.bankCodeManager = bankCodeManager;
    }

    @Autowired
    public void setCurrencyManager(CurrencyManager currencyManager) {
        this.currencyManager = currencyManager;
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
        req.setAttribute(BANK_ACCOUNTS_ATTRIBUTE, getClientBankAccounts(req));
        req.setAttribute(BANK_CODES_ATTRIBUTE, bankCodeManager.getBankCodes());
        req.setAttribute(CURRENCIES_ATTRIBUTE, currencyManager.getAvailableCurrencies());
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String formAction = req.getParameter(ACTION_PARAMETER);
        if (formAction == null) {
            req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);
            doGet(req, resp);
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
            case CANCEL_TRANSACTION_ACTION:
                doCancelTransaction(request, response);
                break;
            default:
                request.setAttribute(ERROR_ATTRIBUTE, "Invalid action!");
                doGet(request, response);
                break;
        }
    }

    private void doCreateTransaction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dateStr = req.getParameter(DATE_PARAMETER),
                accountNumber = req.getParameter(ACCOUNT_NUMBER_PARAMETER),
                offset = req.getParameter(OFFSET_PARAMETER),
                bankCode = req.getParameter(BANK_CODE_PARAMETER),
                amountStr = req.getParameter(AMOUNT_PARAMETER),
                currencyName = req.getParameter(CURRENCY_NAME_PARAMETER),
                constSymbol = req.getParameter(CONST_SYMBOL_PARAMETER),
                varSymbol = req.getParameter(VARIABLE_SYMBOL_PARAMETER),
                specificSymbol = req.getParameter(SPECIFIC_SYMBOL_PARAMETER),
                message = req.getParameter(MESSAGE_PARAMETER);

        req.setAttribute(COPY_PARAMETERS_ATTRIBUTE, true);

        // validate form parameters
        Date dueDate = null;
        try {
            dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            req.setAttribute(ERROR_ATTRIBUTE, e.getLocalizedMessage());
            doGet(req, resp);
            return;
        }
        BigDecimal amount = null;
        try {
            amount = new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            req.setAttribute(ERROR_ATTRIBUTE, "Invalid amount format.");
            doGet(req, resp);
            return;
        }
        Currency currency = null;
        try {
            currency = Currency.valueOf(currencyName);
        } catch (IllegalArgumentException e) {
            req.setAttribute(ERROR_ATTRIBUTE, "Unknown currency.");
            doGet(req, resp);
            return;
        }
        PaymentTransaction transaction = new PaymentTransaction(
                TransactionType.ONE_TIME_PAYMENT_ORDER,
                dueDate, amount, currency, new OffsetAccount(offset, bankCode),
                constSymbol, varSymbol, specificSymbol, message
        );

        try {
            transactionManager.preparePayment(transaction, getAuthenticatedClient(req), accountNumber);
        } catch (PaymentTransactionValidationException | OffsetAccountValidationException | BankAccountValidationException e) {
            req.setAttribute(ERROR_ATTRIBUTE, e.getLocalizedMessage());
            doGet(req, resp);
            return;
        }

        Date timeout = DateUtils.addMinutes(new Date(), verificationSettings.getTransactionCodeTimeout());
        req.removeAttribute(COPY_PARAMETERS_ATTRIBUTE);
        req.getSession().setAttribute(PREPARED_TRANSACTION_SESSION, transaction);
        req.getSession().setAttribute(VERIFICATION_CODE_TIMEOUT_SESSION, timeout);
        req.setAttribute(PREPARED_TRANSACTION_ATTRIBUTE, transaction);
        req.setAttribute(VERIFICATION_CODE_LENGTH_ATTRIBUTE, verificationSettings.getTransactionCodeLength());
        req.setAttribute(VERIFICATION_CODE_TIMEOUT_ATTRIBUTE, verificationSettings.getTransactionCodeTimeout());
        dispatch(VERIFY_TEMPLATE_PATH, req, resp);
    }

    private void doVerifyTransaction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaymentTransaction transaction = (PaymentTransaction) req.getSession().getAttribute(PREPARED_TRANSACTION_SESSION);
        Date timeout = (Date) req.getSession().getAttribute(VERIFICATION_CODE_TIMEOUT_SESSION);
        String code = req.getParameter(VERIFICATION_CODE_PARAMETER);

        // check verification timeout
        Date currentDateTime = new Date();
        try {
            if (currentDateTime.after(timeout)) {
                req.setAttribute(ERROR_ATTRIBUTE, "Timeout expired");
                doCancelTransaction(req, resp);
                return;
            }
        } catch (NullPointerException e) {
            req.setAttribute(ERROR_ATTRIBUTE, "Timeout expired");
            doCancelTransaction(req, resp);
            return;
        }

        try {
            if (transactionManager.verifyPayment(transaction.getId(), code)) {
                req.getSession().removeAttribute(PREPARED_TRANSACTION_SESSION);
                dispatch(SUCCESS_TEMPLATE_PATH, req, resp);
            } else {
                req.setAttribute(ERROR_ATTRIBUTE, "Invalid verification code");
                doCancelTransaction(req, resp);
            }
        } catch (BankAccountValidationException e) {
            errorDispatch(e.getLocalizedMessage(), req, resp);
        }
    }

    private void doCancelTransaction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PaymentTransaction transaction = (PaymentTransaction) req.getSession().getAttribute(PREPARED_TRANSACTION_SESSION);
        req.setAttribute(PREPARED_TRANSACTION_ATTRIBUTE, transaction);
        transactionManager.cancelPayment(transaction.getId());
        req.setAttribute(WARNING_ATTRIBUTE, "Transaction was canceled");
        doGet(req, resp);
    }
}
