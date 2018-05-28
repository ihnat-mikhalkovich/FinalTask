package com.epam.final_task.service.impl;

import com.epam.final_task.entity.UserOrderBy;
import com.epam.final_task.entity.UserPage;
import com.epam.final_task.exception.DAOException;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.dao.DAOFactory;
import com.epam.final_task.dao.UserDAO;
import com.epam.final_task.entity.User;
import com.epam.final_task.service.UserService;
import com.epam.final_task.service.validation.UserServiceValidator;

public class UserServiceImpl implements UserService {

    @Override
    public void addUser(User user, String password) throws ServiceException {
        if (!UserServiceValidator.addUserValidation(user, password)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        try {
            userDAO.addUser(user, password);
        } catch (DAOException e) {
            throw new ServiceException("addUser(...) failed.", e);
        }
    }

    @Override
    public User getUser(int userId) throws ServiceException {
        if (!UserServiceValidator.userIdValidation(userId)) {
            return null;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        User user;
        try {
            user = userDAO.getUser(userId);
        } catch (DAOException e) {
            throw new ServiceException("getUser(...) failed.", e);
        }
        return user;
    }

    @Override
    public boolean userVerification(String email, String password) throws ServiceException {
        if (!UserServiceValidator.userVerificationValidation(email, password)) {
            return false;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        boolean userValid;
        try {
            userValid = userDAO.userVerification(email, password);
        } catch (DAOException e) {
            throw new ServiceException("userVerification(...) failed.", e);
        }
        return userValid;
    }

    @Override
    public void editUser(User user) throws ServiceException {
        if (!UserServiceValidator.userValidation(user)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        try {
            userDAO.editUser(user);
        } catch (DAOException e) {
            throw new ServiceException("editUser(...) failed.", e);
        }
    }

    @Override
    public int getUserId(String email) throws ServiceException {
        if (!UserServiceValidator.emailValidation(email)) {
            return -1;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        int userId = 0;
        try {
            userId = userDAO.getUserId(email);
        } catch (DAOException e) {
            throw new ServiceException("getUserId(...) failed.", e);
        }
        return userId;
    }

    @Override
    public void changePassword(int userId, String newPassword) throws ServiceException {
        if (!UserServiceValidator.changePasswordValidation(userId, newPassword)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        try {
            userDAO.changePassword(userId, newPassword);
        } catch (DAOException e) {
            throw new ServiceException("changePassword(...) failed.", e);
        }
    }

    @Override
    public UserPage getUserPage(int pageNumber, UserOrderBy orderBy) throws ServiceException {
        if (!UserServiceValidator.pageNumberValidation(pageNumber)) {
            return null;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        UserPage userPage = null;
        try {
            userPage = userDAO.getUserPage(pageNumber, orderBy);
        } catch (DAOException e) {
            throw new ServiceException("getUserPage(...) failed.", e);
        }
        return userPage;
    }

    @Override
    public void checkDiscount(int userId) throws ServiceException {
        if (!UserServiceValidator.userIdValidation(userId)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        try {
            userDAO.checkDiscount(userId);
        } catch (DAOException e) {
            throw new ServiceException("checkDiscount(...) failed.", e);
        }
    }

    @Override
    public void raiseBalance(int userId, double raise) throws ServiceException {
        if (!UserServiceValidator.raiseBalanceValidation(userId, raise)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        try {
            userDAO.raiseBalance(userId, raise);
        } catch (DAOException e) {
            throw new ServiceException("raiseBalance(...) failed.", e);
        }
    }

    @Override
    public double getUserBalance(int userId) throws ServiceException {
        if (!UserServiceValidator.userIdValidation(userId)) {
            return -1;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        double balance = -1;
        try {
            balance = userDAO.getUserBalance(userId);
        } catch (DAOException e) {
            throw new ServiceException("getUserBalance(...) failed.", e);
        }
        return  balance;
    }

    @Override
    public void banUser(int userId) throws ServiceException {
        if (!UserServiceValidator.userIdValidation(userId)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        try {
            userDAO.banUser(userId);
        } catch (DAOException e) {
            throw new ServiceException("banUser(...) failed.", e);
        }
    }
}
