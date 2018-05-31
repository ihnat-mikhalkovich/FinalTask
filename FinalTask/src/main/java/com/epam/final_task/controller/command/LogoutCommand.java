package com.epam.final_task.controller.command;

import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.UserRoleEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = request.getParameter(RequestParameterName.CURRENT_PAGE);
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionAttributeName.USER_ID, null);
        session.setAttribute(SessionAttributeName.DISCOUNT, 0);
        session.setAttribute(SessionAttributeName.USER_ROLE, UserRoleEnum.GUEST.getName());
        response.sendRedirect(redirectPage);
    }

}
