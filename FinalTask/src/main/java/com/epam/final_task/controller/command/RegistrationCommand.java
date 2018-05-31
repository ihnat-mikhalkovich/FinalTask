package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.entity.User;
import com.epam.final_task.service.ServiceFactory;
import com.epam.final_task.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class RegistrationCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = request.getParameter(RequestParameterName.CURRENT_PAGE);
        String firstName = request.getParameter(RequestParameterName.FIRST_NAME);
        String lastName = request.getParameter(RequestParameterName.LAST_NAME);
        String password = request.getParameter(RequestParameterName.PASSWORD);
        String tel = request.getParameter(RequestParameterName.TELEPHONE_NUMBER);
        String email = request.getParameter(RequestParameterName.EMAIL);
        Date now = new Date();
        User user = new User(firstName, lastName, tel, email, now);
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        try {
            userService.addUser(user, password);
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        response.sendRedirect(redirectPage);
    }
}
