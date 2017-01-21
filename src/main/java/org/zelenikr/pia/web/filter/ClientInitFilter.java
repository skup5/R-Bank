package org.zelenikr.pia.web.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.manager.ClientManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Prepares client session data.
 *
 * @author Roman Zelenik
 */
public class ClientInitFilter implements Filter {

    private static final String AUTHENTICATED_CLIENT_SESSION = "authenticatedClient";
//    private static final String ERROR_CLIENT_ATTRIBUTE = "errorClient";

    private ClientManager clientManager;

    @Autowired
    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext context = WebApplicationContextUtils
                .getWebApplicationContext(filterConfig.getServletContext());
        AutowireCapableBeanFactory ctx = context.getAutowireCapableBeanFactory();
        ctx.autowireBean(this);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Client client = (Client) httpServletRequest.getSession().getAttribute(AUTHENTICATED_CLIENT_SESSION);
        if (client == null) {
            client = clientManager.loadWithBankAccounts(getAuthenticatedClient().getId());
//            if(client == null){
//                request.setAttribute(ERROR_CLIENT_ATTRIBUTE, "Sorry, but you don't own any bank account. Please contact our helpdesk.");
//                request.getRequestDispatcher("/logout").forward(request, response);
//            }
            httpServletRequest.getSession().setAttribute(AUTHENTICATED_CLIENT_SESSION, client);
        }

        chain.doFilter(request, response);
    }

    private Client getAuthenticatedClient() {
        return (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void destroy() {

    }
}
