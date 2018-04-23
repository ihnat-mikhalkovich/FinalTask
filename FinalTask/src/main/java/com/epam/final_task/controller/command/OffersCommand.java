package com.epam.final_task.controller.command;

import com.epam.final_task.DAO.RoomDAO;
import com.epam.final_task.DAO.impl.RoomDAOImpl;
import com.epam.final_task.entity.LanguageEnum;
import com.epam.final_task.entity.Room;

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
        HttpSession session = request.getSession(true);
        String language = (String) session.getAttribute("language");
        LanguageEnum lang = LanguageEnum.RU;
        if (language != null) {
            lang = LanguageEnum.valueOf(language.toUpperCase());
        }
        RoomDAO roomDAO = new RoomDAOImpl();
        List<Room> rooms = roomDAO.getAllRooms(lang);
        request.setAttribute("rooms", rooms);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/offers.jsp");
        dispatcher.forward(request, response);
    }
}
