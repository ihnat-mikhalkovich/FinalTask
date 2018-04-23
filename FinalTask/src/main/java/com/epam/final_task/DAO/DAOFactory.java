package com.epam.final_task.DAO;

import com.epam.final_task.DAO.impl.RoomDAOImpl;
import com.epam.final_task.DAO.impl.UserDAOImpl;

public class DAOFactory {

    private final static DAOFactory instance = new DAOFactory();

    private final RoomDAO roomDAO = new RoomDAOImpl();

    private final UserDAO userDAO = new UserDAOImpl();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public RoomDAO getRoomDAO() {
        return roomDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}
