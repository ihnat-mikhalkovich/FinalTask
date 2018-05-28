package com.epam.final_task.dao;

import com.epam.final_task.entity.*;
import com.epam.final_task.exception.DAOException;

public interface UserDAO {

    void addUser(User user, String password) throws DAOException;

    User getUser(int userId) throws DAOException;

    UserPage getUserPage(int pageNumber, UserOrderBy orderBy) throws DAOException;

    boolean userVerification(String email, String password) throws DAOException;

    default UserRoleEnum getRole(int userId) throws DAOException {
        User user = getUser(userId);
        return user.getRole();
    }

    void editUser(User user) throws DAOException;

    int getUserId(String email) throws DAOException;

    void changePassword(int userId, String newPassword) throws DAOException;

    void checkDiscount(int userId) throws DAOException;

    void raiseBalance(int userId, double raise) throws DAOException;

    double getUserBalance(int userId) throws DAOException;

    void banUser(int userId) throws DAOException;

}
