package com.epam.final_task.exception;

/**
 * Custom exception class for {@code ConnectionPool}.
 *
 * @author Ihnat Mikhalkovich
 * @see com.epam.final_task.dao.connection_pool.ConnectionPool
 * @since 1.0
 */
public class ConnectionPoolException extends Exception {

    public static final long serialVersionUID = 1L;

    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }

}
