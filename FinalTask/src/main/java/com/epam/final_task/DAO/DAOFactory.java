package com.epam.final_task.dao;

import com.epam.final_task.dao.impl.RoomDAOImpl;
import com.epam.final_task.dao.impl.UserDAOImpl;

/**
 * Provides static methods for get objects UserDAO and RoomDAO.
 * @see UserDAO
 * @see RoomDAO
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class DAOFactory {

    /**
     * Static variable {@code instance} of type {@code DAOFactory}.
     */
    private final static DAOFactory instance = new DAOFactory();

    private final RoomDAO roomDAO = new RoomDAOImpl();

    private final UserDAO userDAO = new UserDAOImpl();

    /** Don't let anyone else instantiate this class */
    private DAOFactory() {
    }

    /**
     * Most of the methods of class {@code DAOFactory} are instance
     * methods and must be invoked with respect to the current runtime object.
     * @return the {@code DAOFactory} object associated with the current Java application.
     */
    public static DAOFactory getInstance() {
        return instance;
    }

    /**
     * Getter for roomDAO.
     * @return RoomDAO implementation instance.
     */
    public RoomDAO getRoomDAO() {
        return roomDAO;
    }

    /**
     * Getter for userDAO.
     * @return UserDAO implementation instance.
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }
}
