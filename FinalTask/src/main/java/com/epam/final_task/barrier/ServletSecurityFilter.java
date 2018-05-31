package com.epam.final_task.barrier;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.controller.command.AccessDirector;
import com.epam.final_task.controller.command.CommandTypeEnum;
import com.epam.final_task.controller.command.CommandTypeMap;
import com.epam.final_task.entity.UserRoleEnum;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter. Protects the servlet from unauthorized access by url.
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
@WebFilter ( urlPatterns = { "/FrontController" }, servletNames = { "FrontController" } )

public class ServletSecurityFilter implements Filter {

    private final CommandTypeMap commandTypeMap = CommandTypeMap.getInstance();

    private final AccessDirector accessDirector = new AccessDirector();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
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
        String commandType = request.getParameter(RequestParameterName.COMMAND_TYPE);
        CommandTypeEnum command = commandTypeMap.getCommand(commandType);
        if (!accessDirector.checkAccess(role, command)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(PageURL.INDEX_PAGE);
            dispatcher.forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
