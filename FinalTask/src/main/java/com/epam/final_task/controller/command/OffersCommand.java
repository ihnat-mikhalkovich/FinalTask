package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.controller.RequestAttributeName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.LanguageEnum;
import com.epam.final_task.entity.Room;
import com.epam.final_task.entity.UserRoleEnum;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class OffersCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardPage = PageURL.OFFERS_PAGE;
        HttpSession session = request.getSession(true);
        String language = (String) session.getAttribute(SessionAttributeName.LANGUAGE);
        LanguageEnum lang = LanguageEnum.RU;
        if (language != null) {
            lang = LanguageEnum.valueOf(language.toUpperCase());
        }
        String userRole = (String) session.getAttribute(SessionAttributeName.USER_ROLE);
        UserRoleEnum role = UserRoleEnum.GUEST;
        if (userRole != null) {
            role = UserRoleEnum.valueOf(userRole.toUpperCase());
        }
        ServiceFactory factory = ServiceFactory.getInstance();
        RoomService roomService = factory.getRoomService();
        List<Room> rooms = null;
        try {
            if (role.ordinal() < UserRoleEnum.USER.ordinal()) {
                rooms = roomService.getAllRoomTypes(lang);
            } else {
                rooms = roomService.getVisibleRooms(lang);
            }
        } catch (ServiceException e) {
            forwardPage = PageURL.ERROR_PAGE;
        }
        request.setAttribute(RequestAttributeName.ROOMS, rooms);
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPage);
        dispatcher.forward(request, response);
    }
}
