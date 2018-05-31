package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.ServiceFactory;
import com.epam.final_task.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditPasswordCommand implements Command {

    private final static String ORDER_BY = "&orderBy=";

    private final static String DIRECTION = "&direction=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderBy = request.getParameter(RequestParameterName.ORDER_BY);
        String direction = request.getParameter(RequestParameterName.DIRECTION);
        int pageNumber = Integer.parseInt(request.getParameter(RequestParameterName.PAGE));
        String redirectPage = PageURL.REQUEST_USERS_PAGE + pageNumber + ORDER_BY + orderBy + DIRECTION + direction;
        int userId = Integer.parseInt(request.getParameter(RequestParameterName.USER_ID));
        String newPassword = request.getParameter(RequestParameterName.NEW_PASSWORD);
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        try{
            userService.changePassword(userId, newPassword);
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        response.sendRedirect(redirectPage);
    }
}
