package com.epam.final_task.dao;

import java.util.ResourceBundle;

/**
 * Class for reading the dao configuration file.
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class DAOParameterManager {

    /**
     * Static variable {@code instance} of type {@code DAOParameterManager}.
     */
    private final static DAOParameterManager instance = new DAOParameterManager();

    private ResourceBundle bundle = ResourceBundle.getBundle("dao");

    /**
     * Most of the methods of class {@code DAOParameterManager} are instance
     * methods and must be invoked with respect to the current runtime object.
     * @return the {@code DAOParameterManager} object associated with the current Java application.
     */
    public static DAOParameterManager getInstance() {
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
