package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.entity.ExtendedRoomTariff;
import com.epam.final_task.entity.LanguageEnum;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class AddTariffSaveCommand implements Command {

    private final static char MEDIUM_SYMBOL = '-';

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = PageURL.REQUEST_TARIFFS_PAGE;
        double cost = Double.parseDouble(request.getParameter(RequestParameterName.COST));
        boolean visibility = request.getParameter(RequestParameterName.VISIBILITY) != null;
        Map<LanguageEnum, String> names = new EnumMap<>(LanguageEnum.class);
        Map<LanguageEnum, String> descriptions = new EnumMap<>(LanguageEnum.class);
        for (LanguageEnum lang : LanguageEnum.values()) {
            String name = request.getParameter(RequestParameterName.NAME + MEDIUM_SYMBOL + lang);
            names.put(lang, name);
            String description = request.getParameter(RequestParameterName.DESCRIPTION + MEDIUM_SYMBOL + lang);
            descriptions.put(lang, description);
        }
        ExtendedRoomTariff tariff = new ExtendedRoomTariff(cost, visibility, names, descriptions);
        ServiceFactory factory = ServiceFactory.getInstance();
        RoomService roomService = factory.getRoomService();
        try {
            roomService.addRoomTariff(tariff);
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        response.sendRedirect(redirectPage);
    }

}
