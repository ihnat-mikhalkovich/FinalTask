package com.epam.final_task.DAO.impl;

import com.epam.final_task.DAO.UserDAO;
import com.epam.final_task.DAO.connection_pool.ConnectionPool;
import com.epam.final_task.Exception.ConnectionPoolException;
import com.epam.final_task.entity.UserRoleEnum;
import com.epam.final_task.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    public final static String SQL_GET_USER = "SELECT first_name, last_name, telephone_number, role FROM hosteldb.users WHERE email=?";

    public final static String SQL_CHECK_USER = "SELECT id FROM hosteldb.users WHERE email=? and password=?";

    public final static String SQL_INSERT_USER = "insert into users (first_name, last_name, password, telephone_number, email, role)" +
            " values (?,?,?,?,?,?)";

    public final static String SQL_CHANGE_PASSWORD = "UPDATE hosteldb.users SET password=? WHERE email=?";

    public UserDAOImpl() {
    }

    @Override
    public boolean isUserInit(String email) {
        User user = getUser(email);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public void addUser(User user, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, user.getTelephoneNumber());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getRole().toString().toLowerCase());
            preparedStatement.execute();
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User getUser(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_USER);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString(1);
                String lastName = resultSet.getString(2);
                String tel = resultSet.getString("telephone_number");
                UserRoleEnum role = UserRoleEnum.valueOf(resultSet.getString("role").toUpperCase());
                user = new User(firstName, lastName, tel, email, role);
            }
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // log
            }
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            connection = connectionPool.takeConnection();
            String usersQuery = "select * from hosteldb.users";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(usersQuery);
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String tel = resultSet.getString("telephone_number");
                String email = resultSet.getString("email");
                UserRoleEnum role = UserRoleEnum.valueOf(resultSet.getString("role").toUpperCase());
                User user = new User(firstName, lastName, tel, email, role);
                users.add(user);
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public boolean checkUser(String email, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean existUser = false;
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(SQL_CHECK_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                existUser = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return existUser;
    }

    @Override
    public boolean changeUserPassword(String email, String oldPassword, String newPassword) {
        boolean validUser = checkUser(email, oldPassword);
        if (!validUser) {
            return false;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_CHANGE_PASSWORD);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
