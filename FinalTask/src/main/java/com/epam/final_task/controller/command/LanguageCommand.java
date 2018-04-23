package com.epam.final_task.controller.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LanguageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter("language");
        HttpSession session = request.getSession(true);
        session.setAttribute("language", lang);
        String currentPage = request.getParameter("currentPage");
        RequestDispatcher dispatcher = request.getRequestDispatcher(currentPage);
        dispatcher.forward(request, response);
    }
}
