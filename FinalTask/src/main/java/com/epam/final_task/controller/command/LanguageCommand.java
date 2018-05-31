package com.epam.final_task.controller.command;

import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.controller.SessionAttributeName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LanguageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter(RequestParameterName.LANGUAGE);
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionAttributeName.LANGUAGE, lang);
        String currentPage = request.getParameter(RequestParameterName.CURRENT_PAGE);
        RequestDispatcher dispatcher = request.getRequestDispatcher(currentPage);
        dispatcher.forward(request, response);
    }
}
