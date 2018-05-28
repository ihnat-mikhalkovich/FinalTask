package com.epam.final_task.controller.command;

import com.epam.final_task.controller.PageURL;
import com.epam.final_task.controller.RequestParameterName;
import com.epam.final_task.entity.User;
import com.epam.final_task.entity.UserRoleEnum;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.service.ServiceFactory;
import com.epam.final_task.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Command implementation. Permits changing user data. Used by the administrator only.
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class EditUserCommand implements Command {

    private final static String DATE_TEMPLATE = "yyyy-MM-dd";

    private final SimpleDateFormat format = new SimpleDateFormat(DATE_TEMPLATE);

    /**
     * Apply changes sent by the administrator.
     *
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException default throw
     * @throws IOException default throw
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see User
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNumber = Integer.parseInt(request.getParameter(RequestParameterName.PAGE));
        String redirectPage = PageURL.REQUEST_USERS_PAGE + pageNumber;
        int userId = Integer.parseInt(request.getParameter(RequestParameterName.USER_ID));
        String firstName = request.getParameter(RequestParameterName.FIRST_NAME);
        String lastName = request.getParameter(RequestParameterName.LAST_NAME);
        String tel = request.getParameter(RequestParameterName.TELEPHONE_NUMBER);
        String email = request.getParameter(RequestParameterName.EMAIL);
        UserRoleEnum role = UserRoleEnum.valueOf(request.getParameter(RequestParameterName.ROLE).toUpperCase());
        int discount = Integer.parseInt(request.getParameter(RequestParameterName.DISCOUNT));
        double balance = Double.parseDouble(request.getParameter(RequestParameterName.BALANCE));

        String registrationDateString = request.getParameter(RequestParameterName.REGISTRATION_DATE);

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
