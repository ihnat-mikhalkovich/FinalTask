package com.epam.final_task.exception;

/**
 * Custom exception class for {@code UserService} and {@code RoomService}.
 *
 * @author Ihnat Mikhalkovich
 * @see com.epam.final_task.service.RoomService
 * @see com.epam.final_task.service.UserService
 * @since 1.0
 */
public class ServiceException extends Exception {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
