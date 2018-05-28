package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestAttributeName;
import com.epam.final_task.entity.LanguageEnum;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddTariffFormCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(RequestAttributeName.LANGUAGE_ENUM, LanguageEnum.values());
        RequestDispatcher dispatcher = request.getRequestDispatcher(PageURL.ADD_TARIFF_FORM_PAGE);
        dispatcher.forward(request, response);
    }

}
