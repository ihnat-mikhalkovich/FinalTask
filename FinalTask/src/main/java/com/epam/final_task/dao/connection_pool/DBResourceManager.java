package com.epam.final_task.dao.connection_pool;

import java.util.ResourceBundle;

/**
 * Class for reading the database connection configuration file.
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class DBResourceManager {

    /**
     * Static variable {@code instance} of type {@code DBResourceManager}.
     */
    private final static DBResourceManager instance = new DBResourceManager();

    private ResourceBundle bundle = ResourceBundle.getBundle("db");

    /**
     * Most of the methods of class {@code DBResourceManager} are instance
     * methods and must be invoked with respect to the current runtime object.
     * @return the {@code DBResourceManager} object associated with the current Java application.
     */
    public static DBResourceManager getInstance() {
        return instance;
    }

    /**
     * Method is required to retrieve values from the configuration file.
     * @param key is needed to find the required configuration field
     * @return key value.
     */
    public String getValue(String key) {
        return bundle.getString(key);
    }

}
