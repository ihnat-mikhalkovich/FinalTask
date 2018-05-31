package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.entity.ExtendedRoom;
import com.epam.final_task.entity.LanguageEnum;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Implementation of realise pattern Command. Add room by business logic.
 *
 * @author Ihnat Mikhalkovich
 * @see Command
 * @since 1.0
 */
public class AddRoomSaveCommand implements Command {

    private final static char MEDIUM_SYMBOL = '-';

    /**
     * Add room by business logic.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException custom exception class for {@code HttpServletRequest} and {@code HttpServletRequest}.
     * @throws IOException execution exclusions
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = PageURL.REQUEST_OFFERS_PAGE;
        int roomsAmount = Integer.parseInt(request.getParameter(RequestParameterName.ROOMS_AMOUNT));
        double cost = Double.parseDouble(request.getParameter(RequestParameterName.COST));
        int numberOfPersons = Integer.parseInt(request.getParameter(RequestParameterName.NUMBER_OF_PERSONS));
        boolean visibility = request.getParameter(RequestParameterName.VISIBILITY) != null;
        String image = request.getParameter(RequestParameterName.IMAGE);
        Map<LanguageEnum, String> names = new EnumMap<>(LanguageEnum.class);
        Map<LanguageEnum, String> descriptions = new EnumMap<>(LanguageEnum.class);
        for (LanguageEnum lang : LanguageEnum.values()) {
            String name = request.getParameter(RequestParameterName.NAME + MEDIUM_SYMBOL + lang);
            names.put(lang, name);
            String description = request.getParameter(RequestParameterName.DESCRIPTION + MEDIUM_SYMBOL + lang);
            descriptions.put(lang, description);
        }
        ExtendedRoom room = new ExtendedRoom(roomsAmount, names, cost, image, descriptions, visibility, numberOfPersons);
        ServiceFactory factory = ServiceFactory.getInstance();
        RoomService roomService = factory.getRoomService();
        try {
            roomService.addRoom(room);
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        response.sendRedirect(redirectPage);
    }

}
