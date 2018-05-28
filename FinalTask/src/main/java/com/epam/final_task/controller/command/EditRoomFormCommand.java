package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.controller.RequestAttributeName;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.entity.ExtendedRoom;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditRoomFormCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardPage = PageURL.EDIT_ROOM_FORM_PAGE;
        int roomId = Integer.parseInt(request.getParameter(RequestParameterName.ROOM_ID));
        ServiceFactory factory = ServiceFactory.getInstance();
        RoomService roomService = factory.getRoomService();
        ExtendedRoom extendedRoom = null;
        try {
            extendedRoom = roomService.getExtendedRoom(roomId);
        } catch(ServiceException e) {
            forwardPage = PageURL.ERROR_PAGE;
        }
        request.setAttribute(RequestAttributeName.EXTENDED_ROOM, extendedRoom);
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPage);
        dispatcher.forward(request, response);
    }

}
