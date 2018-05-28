package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.User;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.ServiceFactory;
import com.epam.final_task.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangePasswordSaveCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = PageURL.REQUEST_PROFILE_PAGE;
        String password = request.getParameter(RequestParameterName.PASSWORD);
        String newPassword = request.getParameter(RequestParameterName.NEW_PASSWORD);
        HttpSession session = request.getSession(true);
        int userId = (Integer) session.getAttribute(SessionAttributeName.USER_ID);
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        User user = null;
        try {
            user = userService.getUser(userId);
            boolean userValid = userService.userVerification(user.getEmail(), password);
            if (userValid) {
                userService.changePassword(userId, newPassword);
            }
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        response.sendRedirect(redirectPage);
    }

}
