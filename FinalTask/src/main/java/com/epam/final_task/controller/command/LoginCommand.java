package com.epam.final_task.controller.command;

import com.epam.final_task.DAO.UserDAO;
import com.epam.final_task.DAO.impl.UserDAOImpl;
import com.epam.final_task.entity.UserRoleEnum;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandType = request.getParameter("commandType");
        String redirectPage = request.getParameter("currentPage");
        HttpSession session = request.getSession(true);
        if (commandType.toLowerCase().equals("logout")){
            session.removeAttribute("userId");
            session.setAttribute("userRole", UserRoleEnum.GUEST.getName());
        } else {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            UserDAO userDAO = new UserDAOImpl();
            boolean userValid = userDAO.checkUser(email, password);
            if (userValid) {
                session.setAttribute("userId", email);
                session.setAttribute("userRole", userDAO.getRole(email).getName());
            }
        }
        response.sendRedirect(redirectPage);
    }

}
