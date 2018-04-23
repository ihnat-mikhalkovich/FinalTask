package com.epam.final_task.controller.command;

import com.epam.final_task.DAO.UserDAO;
import com.epam.final_task.DAO.impl.UserDAOImpl;
import com.epam.final_task.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RegistrationCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String tel = request.getParameter("tel");
        String email = request.getParameter("email");
        User user = new User(firstName, lastName, tel, email);
        UserDAO userDAO = new UserDAOImpl();
        userDAO.addUser(user, password);
        List<User> users = userDAO.getAllUsers();
        for (User tmpUser : users) {
            out.println("<br/>" + tmpUser + "<br/>");
        }

    }
}
