package org.zelenikr.pia.web.servlet.spring.controller;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zelenikr.pia.domain.User;
import org.zelenikr.pia.verification.user.AuthenticationVerificationCodeSender;
import org.zelenikr.pia.verification.user.UserVerifier;
import org.zelenikr.pia.verification.MessageRecipient;
import org.zelenikr.pia.verification.VerificationSettings;
import org.zelenikr.pia.web.auth.LogoutService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * @author Roman Zelenik
 */
@WebServlet("/login-verification")
public class LoginVerificationController extends AbstractController {

    private static final String VIEW_URL = "/view/login-verification";
    private static final String DEFAULT_TEMPLATE_PATH = "loginVerification";

    private static final String LOGIN_URL = "login";

    private static final String
            USER_TARGET_URL_SESSION = "userRequestedUrl",
            VERIFICATION_CODE_TIMEOUT_SESSION = "loginVerificationCodeTimeout",
            VERIFICATED_USER_SESSION = "verificatedLoginUser";

    private static final String
            VERIFICATION_CODE_LENGTH_ATTRIBUTE = "verificationCodeLength",
            VERIFICATION_CODE_TIMEOUT_ATTRIBUTE = "verificationCodeTimeout";

    private static final String
            VERIFICATION_CODE_PARAMETER = "verificationCode",
            ACTION_PARAMETER = "action";

    private static final String
            VERIFY_LOGIN_ACTION = "verify",
            CANCEL_LOGIN_ACTION = "cancel";

    private AuthenticationVerificationCodeSender codeSender;
    private UserVerifier verifier;
    private VerificationSettings verificationSettings;
    private LogoutService logoutService;

    @Autowired
    public void setLogoutService(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @Autowired
    public void setCodeSender(AuthenticationVerificationCodeSender codeSender) {
        this.codeSender = codeSender;
    }

    @Autowired
    public void setVerifier(UserVerifier verifier) {
        this.verifier = verifier;
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
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof MessageRecipient) {
                sendCode(req, resp, principal);
                return;
            }
            logoutService.logout(req);
            errorDispatch("Cannot send verification code", req, resp);
            return;
        }
        resp.sendRedirect(LOGIN_URL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String formAction = req.getParameter(ACTION_PARAMETER);
        if (formAction == null) {
            doCancel(req, resp);
        } else {
            doAction(formAction, req, resp);
        }
    }

    private void doAction(String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (action) {
            case VERIFY_LOGIN_ACTION:
                doVerify(request, response);
                break;
            case CANCEL_LOGIN_ACTION:
                doCancel(request, response);
                break;
            default:
                doCancel(request, response);
//                request.setAttribute(ERROR_ATTRIBUTE, "Invalid action!");
//                doGet(request, response);
                break;
        }
    }

    private void doCancel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logoutService.logout(request);
        response.sendRedirect(LOGIN_URL);
    }

    private void doVerify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("LoginVerificationController.doVerify");
        User user = (User) request.getSession().getAttribute(VERIFICATED_USER_SESSION);
        Date timeout = (Date) request.getSession().getAttribute(VERIFICATION_CODE_TIMEOUT_SESSION);
        String code = request.getParameter(VERIFICATION_CODE_PARAMETER);

        if (user == null) {
            System.out.println("user = null");
            doCancel(request, response);
            return;
        }

        // check verification timeout
        Date currentDateTime = new Date();
        try {
            if (currentDateTime.after(timeout)) {
                System.out.println("timeout");
                request.setAttribute(ERROR_ATTRIBUTE, "Timeout expired");
//                sendCode(request, response, user);
                dispatch(request, response);
                return;
            }
        } catch (NullPointerException e) {
            System.out.println("timeout");
            request.setAttribute(ERROR_ATTRIBUTE, "Timeout expired");
//            sendCode(request, response, user);
            dispatch(request, response);
            return;
        }

        if (verifier.verifyObject(user, code)) {
            String url = (String) request.getSession().getAttribute(USER_TARGET_URL_SESSION);
            if (url == null) {
                url = "home";
            }
            response.sendRedirect(url);
        } else {
            errorDispatch("Invalid verification code", request, response);
        }
    }

    private void sendCode(HttpServletRequest req, HttpServletResponse resp, Object principal) throws ServletException, IOException {
        String code = verifier.generateCode((User) principal);
        if(!codeSender.send(code, (MessageRecipient) principal)){
            logoutService.logout(req);
            errorDispatch("Sorry, error while sending code", req, resp);
            return;
        }

        Date timeout = DateUtils.addMinutes(new Date(), verificationSettings.getAuthenticationCodeTimeout());
        req.getSession().setAttribute(VERIFICATION_CODE_TIMEOUT_SESSION, timeout);
        req.getSession().setAttribute(VERIFICATED_USER_SESSION, principal);

        req.setAttribute(VERIFICATION_CODE_LENGTH_ATTRIBUTE, verificationSettings.getAuthenticationCodeLength());
        req.setAttribute(VERIFICATION_CODE_TIMEOUT_ATTRIBUTE, verificationSettings.getAuthenticationCodeTimeout());

        dispatch(req, resp);
    }


    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
