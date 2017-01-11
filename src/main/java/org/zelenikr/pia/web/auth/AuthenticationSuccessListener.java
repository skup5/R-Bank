package org.zelenikr.pia.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;

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
//        if(event instanceof InteractiveAuthenticationSuccessEvent) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        httpSession.setAttribute(DISPLAY_NAME_PARAMETER, userDetails.getUsername());
        System.out.println("AuthenticationSuccessListener.onAuthenticationSuccess");
//        }
    }

}
