package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestAttributeName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.LanguageEnum;
import com.epam.final_task.entity.RoomTariff;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class TariffsCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardPage = PageURL.TARIFFS_PAGE;
        HttpSession session = request.getSession(true);
        String language = (String) session.getAttribute(SessionAttributeName.LANGUAGE);
        LanguageEnum lang = LanguageEnum.RU;
        if (language != null) {
            lang = LanguageEnum.valueOf(language.toUpperCase());
        }
        ServiceFactory factory = ServiceFactory.getInstance();
        RoomService roomService = factory.getRoomService();
        List<RoomTariff> tariffs = null;
        try {
            tariffs = roomService.getAllRoomTariffs(lang);
        } catch (ServiceException e) {
            forwardPage = PageURL.ERROR_PAGE;
        }
        request.setAttribute(RequestAttributeName.TARIFFS, tariffs);
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPage);
        dispatcher.forward(request, response);
    }
}
