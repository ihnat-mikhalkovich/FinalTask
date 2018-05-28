package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.UserRoleEnum;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.ServiceFactory;
import com.epam.final_task.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ReturnTheRoomCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = request.getParameter(RequestParameterName.CURRENT_PAGE);
        int transactionId = Integer.parseInt(request.getParameter(RequestParameterName.TRANSACTION_ID));
        HttpSession session = request.getSession(true);
        UserRoleEnum role = UserRoleEnum.valueOf(((String) session.getAttribute(SessionAttributeName.USER_ROLE)).toUpperCase());
        int userId = (Integer) session.getAttribute(SessionAttributeName.USER_ID);
        ServiceFactory factory = ServiceFactory.getInstance();
        RoomService roomService = factory.getRoomService();
        try {
            roomService.userReturnsRooms(transactionId);
            if (roomService.checkCancelNumber(userId) && (role != UserRoleEnum.ADMINISTRATOR)) {
                UserService userService = factory.getUserService();
                userService.banUser(userId);
            }
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        response.sendRedirect(redirectPage);
    }

}
