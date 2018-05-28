package com.epam.final_task.barrier;

import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.UserRoleEnum;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter. Protects the folder from unauthorized access by url.
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
@WebFilter(urlPatterns = {"/jsp/user/*", "/jsp/404.jsp"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})

public class UserSecurityFilter implements Filter {

    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        String roleString = (String) session.getAttribute(SessionAttributeName.USER_ROLE);
        UserRoleEnum role;
        if (roleString == null) {
            role = UserRoleEnum.GUEST;
        } else {
            role = UserRoleEnum.valueOf(roleString.toUpperCase());
        }
        if (role.ordinal() > UserRoleEnum.BANNED.ordinal()) {
            response.sendRedirect(indexPath);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

}
