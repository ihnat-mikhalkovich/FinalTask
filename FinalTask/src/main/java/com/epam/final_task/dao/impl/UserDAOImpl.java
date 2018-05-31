package com.epam.final_task.dao.impl;

import com.epam.final_task.dao.DAOParameter;
import com.epam.final_task.dao.DAOParameterManager;
import com.epam.final_task.dao.UserDAO;
import com.epam.final_task.dao.connection_pool.ConnectionPool;
import com.epam.final_task.entity.User;
import com.epam.final_task.entity.UserOrderBy;
import com.epam.final_task.entity.UserPage;
import com.epam.final_task.entity.UserRoleEnum;
import com.epam.final_task.exception.ConnectionPoolException;
import com.epam.final_task.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements the interface UserDAO.
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class UserDAOImpl implements UserDAO {

    /**
     * Fields with {@code SQL} prefix need for connection with database.
     * pageLength set limit of objects in one pack.
     */
    private final static Logger log = Logger.getLogger(UserDAOImpl.class);
    private final static String SQL_GET_USER = "SELECT email, first_name, last_name, telephone_number, role, registration_date, discount, balance FROM users WHERE id=?";
    private final static String SQL_CHECK_USER = "SELECT id FROM users WHERE email=? AND password=?";
    private final static String SQL_INSERT_USER = "INSERT INTO users (first_name, last_name, password, telephone_number, email, role, registration_date)" +
            " VALUES (?,?,?,?,?,?,?)";
    private final static String SQL_GET_USER_ID = "SELECT id FROM users WHERE email=?";
    private final static String SQL_UPDATE_USER = "UPDATE users SET first_name=?, last_name=?, telephone_number=?, email=?, role=?, discount=?, balance=? WHERE id=?";
    private final static String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET password=? WHERE id=?";
    private final static String SQL_GET_USER_PAGE_AND_PAGE_AMOUNT = "SELECT id, first_name, last_name, telephone_number, email, role, registration_date, discount, balance,\n" +
            "  (SELECT ceil(count(id)/?) FROM users)\n" +
            "FROM users ORDER BY %word% LIMIT ? OFFSET ?";
    private final static String SQL_CHECK_DISCOUNT = "UPDATE users SET discount=5\n" +
            "WHERE (SELECT sum(paid) AS sum FROM users_has_rooms WHERE departure<CURDATE() AND users_id=? GROUP BY users_id)>1 AND users.id=? AND discount<5";
    private final static String SQL_UPDATE_BALANCE = "UPDATE users SET balance=round(balance+?, 2) WHERE id=?";
    private final static String SQL_GET_BALANCE = "SELECT balance FROM users WHERE id=?";
    private final static String SQL_UPDATE_BAN_USER = "UPDATE users SET role='banned' WHERE id=?";
    private final static String REPLACEABLE_WORD = "%word%";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private int pageLength;
    private SimpleDateFormat format;

    /**
     * Constructor without params.
     */
    public UserDAOImpl() {
        DAOParameterManager manager = DAOParameterManager.getInstance();
        String dateTemplate = manager.getValue(DAOParameter.DAO_DATE_TEMPLATE);
        format = new SimpleDateFormat(dateTemplate);
        pageLength = Integer.parseInt(manager.getValue(DAOParameter.DAO_USER_PAGE_LENGTH));
    }

    /**
     * Add new User in database.
     *
     * @param user     new user.
     * @param password password of new user.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see User
     */
    @Override
    public void addUser(User user, String password) throws DAOException {
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
            preparedStatement.setDate(7, new java.sql.Date(user.getRegistrationDate().getTime()));
            preparedStatement.execute();
        } catch (ConnectionPoolException | SQLException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method addUser(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Get user information from database by userId.
     *
     * @param userId id of user.
     * @return User
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see User
     */
    @Override
    public User getUser(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_USER);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String email = resultSet.getString(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String tel = resultSet.getString(4);
                UserRoleEnum role = UserRoleEnum.valueOf(resultSet.getString(5).toUpperCase());
                java.util.Date registrationDate = format.parse(resultSet.getString(6));
                int discount = resultSet.getInt(7);
                double balance = resultSet.getDouble(8);
                user = new User(userId, firstName, lastName, tel, email, role, registrationDate, discount, balance);
            }
        } catch (SQLException | ConnectionPoolException | ParseException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getUser(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return user;
    }

    /**
     * Checks the user for existence.
     *
     * @param email    email of user
     * @param password password of user
     * @return if user with email and password exist return true, else false.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public boolean userVerification(String email, String password) throws DAOException {
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
            log.warn("The method was not completed.", e);
            throw new DAOException("Method userVerification(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return existUser;
    }

    /**
     * Edit information about user.
     *
     * @param user user with edited information.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public void editUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getTelephoneNumber());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getRole().toString().toLowerCase());
            preparedStatement.setInt(6, user.getDiscount());
            preparedStatement.setDouble(7, user.getBalance());
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method editUser(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Get userId of user by email.
     *
     * @param email email of user.
     * @return userId
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public int getUserId(String email) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int userId = 0;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_USER_ID);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getUserId(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return userId;
    }

    /**
     * Change password of user.
     *
     * @param userId      id of user
     * @param newPassword new password of chosen user.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public void changePassword(int userId, String newPassword) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method changePassword(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Produces the required page.
     *
     * @param pageNumber number of page.
     * @return UserPage
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see UserPage
     */
    @Override
    public UserPage getUserPage(int pageNumber, UserOrderBy orderBy) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        String prepareStatementString = SQL_GET_USER_PAGE_AND_PAGE_AMOUNT.replaceFirst(REPLACEABLE_WORD, orderBy.getOrderBy().getName() + " " + orderBy.getDirection().getName());
        ResultSet resultSet = null;
        UserPage userPage = new UserPage();
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(prepareStatementString);
            statement.setInt(1, pageLength);
            statement.setInt(2, pageLength);
            statement.setInt(3, (pageNumber - 1) * pageLength);
            resultSet = statement.executeQuery();
            List<User> users = new LinkedList<>();
            int pageAmount = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String tel = resultSet.getString(4);
                String email = resultSet.getString(5);
                UserRoleEnum role = UserRoleEnum.valueOf(resultSet.getString(6).toUpperCase());
                java.util.Date registrationDate = format.parse(resultSet.getString(7));
                int discount = resultSet.getInt(8);
                double balance = resultSet.getDouble(9);
                User user = new User(id, firstName, lastName, tel, email, role, registrationDate, discount, balance);
                users.add(user);
                pageAmount = resultSet.getInt(10);
            }
            userPage.setUsers(users);
            userPage.setPageAmount(pageAmount);
            userPage.setCurrentPage(pageNumber);
            userPage.setOrderBy(orderBy);
        } catch (ConnectionPoolException | SQLException | ParseException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getUserPage(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return userPage;
    }

    /**
     * Checks the user for a discount for him.
     *
     * @param userId id of user.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public void checkDiscount(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement checkDiscount = null;
        try {
            connection = connectionPool.takeConnection();
            checkDiscount = connection.prepareStatement(SQL_CHECK_DISCOUNT);
            checkDiscount.setInt(1, userId);
            checkDiscount.setInt(2, userId);
            checkDiscount.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method checkDiscount(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, checkDiscount);
        }
    }

    /**
     * Used to replenish the balance of the user.
     *
     * @param userId user ID
     * @param raise  amount of replenishment
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public void raiseBalance(int userId, double raise) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_BALANCE);
            preparedStatement.setDouble(1, raise);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method raiseBalance(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Used to view the user's balance.
     *
     * @param userId user ID
     * @return user balance
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public double getUserBalance(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        double balance = -1;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_BALANCE);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getDouble(1);
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method raiseBalance(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return balance;
    }

    /**
     * Ban user by id
     *
     * @param userId id of user
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public void banUser(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_BAN_USER);
            preparedStatement.setDouble(1, userId);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method raiseBalance(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }
}
