package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestAttributeName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.User;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.ServiceFactory;
import com.epam.final_task.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProfileCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardPage = PageURL.PROFILE_PAGE;
        HttpSession session = request.getSession(true);
        int userId = (Integer) session.getAttribute(SessionAttributeName.USER_ID);
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        User user = null;
        try {
            user = userService.getUser(userId);
        } catch (ServiceException e) {
            forwardPage = PageURL.ERROR_PAGE;
        }
        request.setAttribute(RequestAttributeName.USER, user);
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPage);
        dispatcher.forward(request, response);
    }

}
