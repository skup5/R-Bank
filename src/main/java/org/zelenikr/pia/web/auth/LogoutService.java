package org.zelenikr.pia.web.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roman Zelenik
 */
@Service
public class LogoutService {

    /**
     * Causes a logout to be completed. The method must complete successfully.
     * Requires the request to be passed in.
     *
     * @param request from which to obtain a HTTP session (cannot be null)
     */
    public void logout(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, null, null);
        }
    }
}
