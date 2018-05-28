package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestAttributeName;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.entity.SortDirectionEnum;
import com.epam.final_task.entity.UserFieldEnum;
import com.epam.final_task.entity.UserOrderBy;
import com.epam.final_task.entity.UserPage;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.ServiceFactory;
import com.epam.final_task.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardPage = PageURL.USERS_PAGE;
        int pageNumber = Integer.parseInt(request.getParameter(RequestParameterName.PAGE));
        UserFieldEnum orderByField = UserFieldEnum.valueOf(request.getParameter(RequestParameterName.ORDER_BY).toUpperCase());
        SortDirectionEnum direction = SortDirectionEnum.valueOf(request.getParameter(RequestParameterName.DIRECTION).toUpperCase());
        UserOrderBy orderBy = new UserOrderBy(orderByField, direction);
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        UserPage userPage = null;
        try {
            userPage = userService.getUserPage(pageNumber, orderBy);
        } catch (ServiceException e) {
            forwardPage = PageURL.ERROR_PAGE;
        }
        request.setAttribute(RequestAttributeName.USER_PAGE, userPage);
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPage);
        dispatcher.forward(request, response);
    }

}
