package com.epam.final_task.controller.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.entity.RoomTariff;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.controller.RequestAttributeName;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.LanguageEnum;
import com.epam.final_task.entity.Room;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.ServiceFactory;

public class ReservationCommand implements Command {

    public final static String DATE_TEMPLATE = "yyyy-MM-dd";

    private final static String REQUEST_MEDIUM_SYMBOLS = " - ";

    private final static String RESPONSE_MEDIUM_SYMBOLS = "+-+";

    private final static int ARRIVAL_DATE_INDEX = 0;

    private final static int DEPARTURE_DATE_INDEX = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardPage = PageURL.RESERVATION_PAGE;
        SimpleDateFormat format = new SimpleDateFormat(DATE_TEMPLATE);
        String dateRangeString = request.getParameter(RequestParameterName.DATE_RANGE);
        String[] dateRange = dateRangeString.split(REQUEST_MEDIUM_SYMBOLS);
        String arrivalDateString = dateRange[ARRIVAL_DATE_INDEX];
        String departureDateString = dateRange[DEPARTURE_DATE_INDEX];
        Date arrivalDate = null;
        Date departureDate = null;
        try {
            arrivalDate = format.parse(arrivalDateString);
            departureDate = format.parse(departureDateString);
        } catch (ParseException e) {
            forwardPage = PageURL.ERROR_PAGE;
        }
        HttpSession session = request.getSession(true);
        String language = (String) session.getAttribute(SessionAttributeName.LANGUAGE);
        LanguageEnum lang = LanguageEnum.RU;
        if (language != null) {
            lang = LanguageEnum.valueOf(language.toUpperCase());
        }
        ServiceFactory factory = ServiceFactory.getInstance();
        RoomService roomService = factory.getRoomService();
        List<Room> rooms = null;
        List<RoomTariff> tariffs = null;
        try {
            rooms = roomService.getAllEmptyVisibleRoomsInRange(arrivalDate, departureDate, lang);
            tariffs = roomService.getAllVisibleRoomTariffs(lang);
        } catch (ServiceException e) {
            forwardPage = PageURL.ERROR_PAGE;
        }
        String responseDateRange = arrivalDateString + RESPONSE_MEDIUM_SYMBOLS + departureDateString;
        request.setAttribute(RequestParameterName.DATE_RANGE, responseDateRange);
        request.setAttribute(RequestParameterName.ARRIVAL_DATE, arrivalDateString);
        request.setAttribute(RequestParameterName.DEPARTURE_DATE, departureDateString);
        request.setAttribute(RequestAttributeName.ROOMS, rooms);
        request.setAttribute(RequestAttributeName.TARIFFS, tariffs);
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPage);
        dispatcher.forward(request, response);
    }
}
