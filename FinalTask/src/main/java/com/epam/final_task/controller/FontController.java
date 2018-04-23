package com.epam.final_task.controller;

import com.epam.final_task.controller.command.Command;
import com.epam.final_task.controller.command.CommandDirector;
import com.epam.final_task.controller.command.CommandTypeEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FontController", value = "/FontController")
public class FontController extends HttpServlet {

    private CommandDirector commandDirector = new CommandDirector();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandType = request.getParameter("commandType");
        CommandTypeEnum commandTypeEnum = CommandTypeEnum.valueOf(commandType.toUpperCase());
        Command command = commandDirector.getCommand(commandTypeEnum);
        command.execute(request, response);
    }

}
