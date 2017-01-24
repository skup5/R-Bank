package org.zelenikr.pia.web.servlet.spring.controller.client.pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.bankcode.BankCodeManager;
import org.zelenikr.pia.domain.Currency;
import org.zelenikr.pia.domain.PatternPaymentOrder;
import org.zelenikr.pia.manager.CurrencyManager;
import org.zelenikr.pia.manager.PatternPaymentOrderManager;
import org.zelenikr.pia.validation.exception.PatternPaymentOrderValidationException;
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
 * Servlet handling client's edit pattern of payment order request.
 *
 * @author Roman Zelenik
 */
@WebServlet("/client/payment-pattern-edit")
public class EditPatternPaymentOrderController extends AbstractClientController {

    private static final String VIEW_URL = "/view/client/payment-pattern";
    private static final String DEFAULT_TEMPLATE_PATH = "client/editPatternPayment";

    private static final String EDITED_PATTERN_SESSION = "editedPattern";

    private static final String
            BANK_CODES_ATTRIBUTE = "bankCodes",
            CURRENCIES_ATTRIBUTE = "currencies",
            BANK_ACCOUNTS_ATTRIBUTE = "bankAccounts",
            PREPARED_PATTERN_ATTRIBUTE = "preparedPatternOrder";

    private static final String
            NAME_PARAMETER = "inputPatternName",
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

    private PatternPaymentOrderManager patternPaymentOrderManager;
    private BankCodeManager bankCodeManager;
    private CurrencyManager currencyManager;

    @Autowired
    public void setPatternPaymentOrderManager(PatternPaymentOrderManager patternPaymentOrderManager) {
        this.patternPaymentOrderManager = patternPaymentOrderManager;
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
        PatternPaymentOrder editedPattern = (PatternPaymentOrder) req.getSession().getAttribute(EDITED_PATTERN_SESSION);
        if (editedPattern == null) {
            resp.sendRedirect("payment-pattern-list");
            return;
        }
        req.setAttribute(PREPARED_PATTERN_ATTRIBUTE, editedPattern);
        req.setAttribute(BANK_ACCOUNTS_ATTRIBUTE, getClientBankAccounts(req));
        req.setAttribute(BANK_CODES_ATTRIBUTE, bankCodeManager.getBankCodes());
        req.setAttribute(CURRENCIES_ATTRIBUTE, currencyManager.getAvailableCurrencies());
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PatternPaymentOrder editedPattern = (PatternPaymentOrder) req.getSession().getAttribute(EDITED_PATTERN_SESSION);
        if (editedPattern == null) {
            resp.sendRedirect("payment-pattern-list");
            return;
        }
        String name = req.getParameter(NAME_PARAMETER),
                dateStr = req.getParameter(DATE_PARAMETER),
                accountNumber = req.getParameter(ACCOUNT_NUMBER_PARAMETER),
                offset = req.getParameter(OFFSET_PARAMETER),
                bankCode = req.getParameter(BANK_CODE_PARAMETER),
                amountStr = req.getParameter(AMOUNT_PARAMETER),
                currencyName = req.getParameter(CURRENCY_NAME_PARAMETER),
                constSymbol = req.getParameter(CONST_SYMBOL_PARAMETER),
                varSymbol = req.getParameter(VARIABLE_SYMBOL_PARAMETER),
                specificSymbol = req.getParameter(SPECIFIC_SYMBOL_PARAMETER),
                message = req.getParameter(MESSAGE_PARAMETER);


        // validate form parameters
        Date dueDate = null;
        try {
            if (!dateStr.isEmpty())
                dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            req.setAttribute(ERROR_ATTRIBUTE, e.getLocalizedMessage());
            doGet(req, resp);
            return;
        }
        BigDecimal amount = null;
        try {
            if (!amountStr.isEmpty())
                amount = new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            req.setAttribute(ERROR_ATTRIBUTE, "Invalid amount format.");
            doGet(req, resp);
            return;
        }
        Currency currency = null;
        try {
            if (!currencyName.isEmpty())
                currency = Currency.valueOf(currencyName);
        } catch (IllegalArgumentException e) {
            req.setAttribute(ERROR_ATTRIBUTE, "Unknown currency.");
            doGet(req, resp);
            return;
        }
        editedPattern.setName(name);
        editedPattern.setAmount(amount);
        editedPattern.setConstSymbol(constSymbol);
        editedPattern.setCurrency(currency);
        editedPattern.setDueDate(dueDate);
        editedPattern.setMessage(message);
        editedPattern.getOffsetAccount().setBankCode(bankCode);
        editedPattern.getOffsetAccount().setOffsetAccountNumber(offset);
        editedPattern.setSpecificSymbol(specificSymbol);
        editedPattern.setVariableSymbol(varSymbol);
        try {
            patternPaymentOrderManager.update(editedPattern, getAuthenticatedClient(req), accountNumber);
        } catch (PatternPaymentOrderValidationException e) {
            req.setAttribute(ERROR_ATTRIBUTE, e.getLocalizedMessage());
            doGet(req, resp);
            return;
        }

        req.setAttribute(SUCCESS_ATTRIBUTE, "Pattern of payment order was successfully changed.");
        doGet(req, resp);
    }
}
