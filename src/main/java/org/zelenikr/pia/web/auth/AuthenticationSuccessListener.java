package org.zelenikr.pia.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.zelenikr.pia.domain.User;

import javax.servlet.http.HttpSession;

/**
 * @author Roman Zelenik
 */
public class AuthenticationSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private static final String DISPLAY_NAME_PARAMETER = "displayName";

    @Autowired
    private HttpSession httpSession;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        User user = (User) event.getAuthentication().getPrincipal();
        httpSession.setAttribute(DISPLAY_NAME_PARAMETER, user.displayName());
        //System.out.println("AuthenticationSuccessListener.onAuthenticationSuccess");
    }

}
