package com.epam.final_task.controller;

import com.epam.final_task.controller.command.Command;
import com.epam.final_task.controller.command.CommandDirector;
import com.epam.final_task.controller.command.CommandTypeEnum;
import com.epam.final_task.controller.command.CommandTypeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet.
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
@WebServlet(name = "FrontController", value = "/FrontController")
public class FrontController extends HttpServlet {

    private final CommandDirector commandDirector = new CommandDirector();

    private final CommandTypeMap commandTypeMap = CommandTypeMap.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandType = request.getParameter(RequestParameterName.COMMAND_TYPE);
        CommandTypeEnum commandTypeEnum = commandTypeMap.getCommand(commandType);
        Command command = commandDirector.getCommand(commandTypeEnum);
        command.execute(request, response);
    }

}
