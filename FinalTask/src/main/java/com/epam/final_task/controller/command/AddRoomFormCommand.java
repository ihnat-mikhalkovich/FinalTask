package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestAttributeName;
import com.epam.final_task.entity.LanguageEnum;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of realise pattern Command. Get add room form.
 *
 * @author Ihnat Mikhalkovich
 * @see Command
 * @since 1.0
 */
public class AddRoomFormCommand implements Command {

    /**
     * Get add room form.
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
        request.setAttribute(RequestAttributeName.LANGUAGE_ENUM, LanguageEnum.values());
        RequestDispatcher dispatcher = request.getRequestDispatcher(PageURL.ADD_ROOM_FORM_PAGE);
        dispatcher.forward(request, response);
    }

}
