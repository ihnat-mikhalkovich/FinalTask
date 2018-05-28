package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.User;
import com.epam.final_task.entity.UserRoleEnum;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.ServiceFactory;
import com.epam.final_task.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = request.getParameter(RequestParameterName.CURRENT_PAGE);
        HttpSession session = request.getSession(true);
        int userId = 0;
        String email = request.getParameter(RequestParameterName.EMAIL);
        String password = request.getParameter(RequestParameterName.PASSWORD);
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        boolean userValid = false;
        try {
            userValid = userService.userVerification(email, password);
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        int discount = 0;
        UserRoleEnum userRole = UserRoleEnum.GUEST;
        if (userValid) {
            try {
                userId = userService.getUserId(email);
                User user = userService.getUser(userId);
                discount = user.getDiscount();
                userRole = user.getRole();
            } catch (ServiceException e) {
                redirectPage = PageURL.ERROR_PAGE;
            }
            session.setAttribute(SessionAttributeName.USER_ID, userId);
            session.setAttribute(SessionAttributeName.DISCOUNT, discount);
            session.setAttribute(SessionAttributeName.USER_ROLE, userRole.getName());
        }
        response.sendRedirect(redirectPage);
    }

}
