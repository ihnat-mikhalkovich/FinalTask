package com.epam.final_task.service;

import com.epam.final_task.service.impl.RoomServiceImpl;
import com.epam.final_task.service.impl.UserServiceImpl;

/**
 * Provides static methods for get objects UserService and RoomService.
 * @see UserService
 * @see RoomService
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class ServiceFactory {

    /**
     * Static variable {@code instance} of type {@code ServiceFactory}.
     */
    private final static ServiceFactory instance = new ServiceFactory();

    private final RoomService roomService = new RoomServiceImpl();

    private final UserService userService = new UserServiceImpl();

    /** Don't let anyone else instantiate this class */
    private ServiceFactory() {
    }

    /**
     * Most of the methods of class {@code ServiceFactory} are instance
     * methods and must be invoked with respect to the current runtime object.
     * @return the {@code ServiceFactory} object associated with the current Java application.
     */
    public static ServiceFactory getInstance() {
        return instance;
    }

    /**
     * Getter for roomService.
     * @return RoomService implementation instance.
     */
    public RoomService getRoomService() {
        return roomService;
    }

    /**
     * Getter for userService.
     * @return UserService implementation instance.
     */
    public UserService getUserService() {
        return userService;
    }

}
