package org.zelenikr.pia.web.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Sends redirecting to previously url or to default url after successful authentication.
 * Also sets to user authentication timeout.
 *
 * @author Roman Zelenik
 */
public class DefaultAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final int UNKNOWN_TIMEOUT = -1;
    protected Log logger = LogFactory.getLog(this.getClass());

    private static final String USER_HOME_URL_SESSION = "displayNameUrl";
    private static final String USER_AUTHENTICATION_TIMEOUT = "authenticationTimeout";

    private RequestCache requestCache = new HttpSessionRequestCache();

    private int clientAuthTimeout = UNKNOWN_TIMEOUT;
    private int adminAuthTimeout = UNKNOWN_TIMEOUT;
    private String clientDefaultTargetUrl = null;
    private String adminDefaultTargetUrl = null;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            handle(request, response, authentication);
            return;
        }
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request
                .getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            handle(request, response, authentication);
            return;
        }

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        changeSessionTimeout(request, authentication);
        if (request.getSession().getAttribute(USER_HOME_URL_SESSION) == null)
            request.getSession().setAttribute(USER_HOME_URL_SESSION, determineDefaultUrl(authentication));

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineDefaultUrl(authentication);
        changeSessionTimeout(request, authentication);
        if (targetUrl == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        request.getSession().setAttribute(USER_HOME_URL_SESSION, targetUrl);
        targetUrl = "/" + targetUrl;
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void changeSessionTimeout(HttpServletRequest request, Authentication authentication) {
        int sessionTimeout = determineSessionTimeout(authentication);
        if (sessionTimeout >= 0) {
            request.getSession().setMaxInactiveInterval(sessionTimeout * 60);
            request.getSession().setAttribute(USER_AUTHENTICATION_TIMEOUT, sessionTimeout);
        }
    }

    /**
     * Determines the target URL by {@link GrantedAuthority}
     *
     * @return url or null
     */
    protected String determineDefaultUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_CLIENT")) {
                return clientDefaultTargetUrl;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                return adminDefaultTargetUrl;
            }
        }
        return null;
    }

    /**
     * Determines the session max inactive interval by {@link GrantedAuthority}
     *
     * @return time in minutes or UNKNOWN_TIMEOUT
     */
    protected int determineSessionTimeout(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_CLIENT")) {
                return clientAuthTimeout;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                return adminAuthTimeout;
            }
        }
        return UNKNOWN_TIMEOUT;
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    /**
     * @param adminAuthTimeout minutes
     */
    public void setAdminAuthTimeout(int adminAuthTimeout) {
        this.adminAuthTimeout = adminAuthTimeout;
    }

    /**
     * @param clientAuthTimeout minutes
     */
    public void setClientAuthTimeout(int clientAuthTimeout) {
        this.clientAuthTimeout = clientAuthTimeout;
    }

    public void setAdminDefaultTargetUrl(String adminDefaultTargetUrl) {
        this.adminDefaultTargetUrl = adminDefaultTargetUrl;
    }

    public void setClientDefaultTargetUrl(String clientDefaultTargetUrl) {
        this.clientDefaultTargetUrl = clientDefaultTargetUrl;
    }
}
