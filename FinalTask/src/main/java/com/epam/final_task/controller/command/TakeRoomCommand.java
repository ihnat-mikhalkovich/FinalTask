package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.DateRange;
import com.epam.final_task.entity.ReservedRoom;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeRoomCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = PageURL.REQUEST_HISTORY_PAGE;
        SimpleDateFormat format = new SimpleDateFormat(ReservationCommand.DATE_TEMPLATE);
        String arrivalDateString = request.getParameter(RequestParameterName.ARRIVAL_DATE);
        String departureDateString = request.getParameter(RequestParameterName.DEPARTURE_DATE);
        Date arrivalDate = null;
        Date departureDate = null;
        try {
            arrivalDate = format.parse(arrivalDateString);
            departureDate = format.parse(departureDateString);
        } catch (ParseException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        int roomId = Integer.parseInt(request.getParameter(RequestParameterName.ROOM_ID));
        int roomsAmount = Integer.parseInt(request.getParameter(RequestParameterName.ROOMS_AMOUNT));
        int tariffId = Integer.parseInt(request.getParameter(RequestParameterName.TARIFF_ID));
        HttpSession session = request.getSession(true);
        int userId = (Integer) session.getAttribute(SessionAttributeName.USER_ID);
        ReservedRoom reservedRoom = new ReservedRoom(userId, roomId, roomsAmount, new DateRange(arrivalDate, departureDate), tariffId);
        ServiceFactory factory = ServiceFactory.getInstance();
        RoomService roomService = factory.getRoomService();
        try {
            roomService.userGetRooms(reservedRoom);
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        response.sendRedirect(redirectPage);
    }

}
