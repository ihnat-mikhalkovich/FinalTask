package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.controller.SessionAttributeName;
import com.epam.final_task.entity.User;
import com.epam.final_task.entity.UserRoleEnum;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.ServiceFactory;
import com.epam.final_task.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditProfileSaveCommand implements Command {

    private final static String DATE_TEMPLATE = "yyyy-MM-dd";

    private final SimpleDateFormat format = new SimpleDateFormat(DATE_TEMPLATE);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirectPage = PageURL.REQUEST_PROFILE_PAGE;
        String firstName = request.getParameter(RequestParameterName.FIRST_NAME);
        String lastName = request.getParameter(RequestParameterName.LAST_NAME);
        String tel = request.getParameter(RequestParameterName.TELEPHONE_NUMBER);
        String email = request.getParameter(RequestParameterName.EMAIL);
        HttpSession session = request.getSession(true);
        int userId = (Integer) session.getAttribute(SessionAttributeName.USER_ID);
        UserRoleEnum role = UserRoleEnum.valueOf(((String) session.getAttribute(SessionAttributeName.USER_ROLE)).toUpperCase());
        int discount = (Integer) session.getAttribute(SessionAttributeName.DISCOUNT);
        String registrationDateString = request.getParameter(RequestParameterName.REGISTRATION_DATE);
        double balance = Double.parseDouble(request.getParameter(RequestParameterName.BALANCE));

        Date registrationDate = null;
        try {
            registrationDate = format.parse(registrationDateString);
        } catch (ParseException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }

        User user = new User(userId, firstName, lastName, tel, email, role, registrationDate, discount, balance);
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        try {
            userService.editUser(user);
        } catch (ServiceException e) {
            redirectPage = PageURL.ERROR_PAGE;
        }
        response.sendRedirect(redirectPage);
    }

}
