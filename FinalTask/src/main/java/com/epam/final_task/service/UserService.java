package com.epam.final_task.service;

import com.epam.final_task.entity.*;
import com.epam.final_task.exception.ServiceException;

/**
 * Class provides methods to verify the arguments UserService methods
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public interface UserService {

    /**
     * Add user in database.
     *
     * @param user adding user
     * @param password user password
     * @throws ServiceException wrap of DAOException
     * @see ServiceException
     * @see User
     */
    void addUser(User user, String password) throws ServiceException;

    /**
     * Get user from database.
     *
     * @param userId for search in database by user id
     * @return User object of user.
     * @throws ServiceException wrap of DAOException
     * @see ServiceException
     * @see User
     */
    User getUser(int userId) throws ServiceException;

    UserPage getUserPage(int pageNumber, UserOrderBy orderBy) throws ServiceException;

    boolean userVerification(String email, String password) throws ServiceException;

    default UserRoleEnum getRole(int userId) throws ServiceException {
        User user = getUser(userId);
        return user.getRole();
    }

    void editUser(User user) throws ServiceException;

    int getUserId(String email) throws ServiceException;

    void changePassword(int userId, String newPassword) throws ServiceException;

    void checkDiscount(int userId) throws ServiceException;

    void raiseBalance(int userId, double raise) throws ServiceException;

    double getUserBalance(int userId) throws ServiceException;

    void banUser(int userId) throws ServiceException;

}
