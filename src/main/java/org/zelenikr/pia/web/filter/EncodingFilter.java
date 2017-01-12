package org.zelenikr.pia.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Sets request and response UTF-8 character encoding.
 *
 * @author Roman Zelenik
 */
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
//        servletRequest.getServletContext().log("EncodingFilter");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
