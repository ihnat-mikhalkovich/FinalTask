package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllowOrderCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNumber = Integer.parseInt(request.getParameter(RequestParameterName.PAGE));
        String redirectPage = PageURL.REQUEST_ORDERS_PAGE + pageNumber;
        int transactionId = Integer.parseInt(request.getParameter(RequestParameterName.TRANSACTION_ID));
        ServiceFactory factory = ServiceFactory.getInstance();
        RoomService roomService = factory.getRoomService();
        try {
            roomService.setApproval(transactionId);
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        response.sendRedirect(redirectPage);
    }

}
