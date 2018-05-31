package com.epam.final_task.exception;

/**
 * Custom exception class for {@code UserDAO} and {@code RoomDAO}.
 *
 * @author Ihnat Mikhalkovich
 * @see com.epam.final_task.dao.RoomDAO
 * @see com.epam.final_task.dao.UserDAO
 * @since 1.0
 */
public class DAOException extends Exception {

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

}
