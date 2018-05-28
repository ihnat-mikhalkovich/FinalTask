package com.epam.final_task.dao.impl;

import com.epam.final_task.dao.DAOParameter;
import com.epam.final_task.dao.DAOParameterManager;
import com.epam.final_task.dao.RoomDAO;
import com.epam.final_task.dao.connection_pool.ConnectionPool;
import com.epam.final_task.entity.*;
import com.epam.final_task.exception.ConnectionPoolException;
import com.epam.final_task.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

/**
 * This class implements the interface RoomDAO.
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class RoomDAOImpl implements RoomDAO {

    /**
     * Fields with {@code SQL} prefix need for connection with database.
     */
    private final static Logger log = Logger.getLogger(RoomDAOImpl.class);
    private final static String SQL_GET_ALL_ROOMS = "SELECT rooms.id, rooms_amount, cost, image, t.name, t.description, number_of_persons FROM rooms\n" +
            "CROSS JOIN rooms_translate t ON rooms.id = t.rooms_id\n" +
            "CROSS JOIN language l ON t.language_id = l.id WHERE l.name=?";
    private final static String SQL_GET_VISIBLE_ROOMS = "SELECT r.id, rooms_amount, cost, image, t.name, t.description, number_of_persons FROM rooms AS r\n" +
            "  CROSS JOIN rooms_translate t ON r.id = t.rooms_id\n" +
            "  CROSS JOIN language l ON t.language_id = l.id WHERE l.name=? AND r.visibility>'0'";
    private final static String SQL_INSERT_ROOM = "INSERT INTO rooms (rooms.id, rooms_amount, cost, image, visibility, number_of_persons)\n" +
            "VALUES (NULL, ?, ?, ?, ?, ?)";
    private final static String SQL_INSERT_ROOM_TRANSLATE = "INSERT INTO rooms_translate (rooms_id, language_id, name, description)\n" +
            "VALUES (LAST_INSERT_ID(), (SELECT id FROM language WHERE language.name=?), ?, ?)";
    private final static String SQL_GET_ROOM = "SELECT rooms_amount, cost, image, name, description, number_of_persons FROM (SELECT r.id, rooms_amount, cost, image, visibility, t.name, t.description, number_of_persons FROM rooms AS r\n" +
            "  CROSS JOIN rooms_translate t ON r.id = t.rooms_id\n" +
            "  CROSS JOIN language l ON t.language_id = l.id WHERE l.name=?) AS subselect WHERE id=?";
    private final static String SQL_GET_EXTENDED_ROOM = "SELECT rooms_amount, cost, image, visibility, t.name, t.description, l.name, number_of_persons FROM rooms AS r\n" +
            "  CROSS JOIN rooms_translate t ON r.id = t.rooms_id\n" +
            "  CROSS JOIN language l ON t.language_id = l.id WHERE r.id=?";
    private final static String SQL_ADD_ENTRY_IN_USERS_HAS_ROOMS = "INSERT INTO users_has_rooms (users_id, rooms_id, rooms_amount, arrival, departure, tariffs_id)\n" +
            "  VALUES (?, ?, ?, ?, ?, ?)";
    private final static String SQL_CANCEL_ENTRY_FROM_USERS_HAS_ROOMS_BY_ID = "UPDATE users_has_rooms SET canceled='1' WHERE id=?";
    private final static String SQL_UPDATE_ROOMS = "UPDATE rooms SET rooms_amount=?, cost=?, image=?, visibility=?, number_of_persons=? WHERE id=?";
    private final static String SQL_UPDATE_ROOMS_TRANSLATE = "UPDATE rooms_translate SET name=?, description=? WHERE rooms_id=? AND language_id=(SELECT id FROM language WHERE language.name=?)";
    private final static String SQL_SET_DATE_RANGE = "SET @arrival:=?, @departure:=?";
    private final static String SQL_GET_ALL_EMPTY_ROOMS = "SELECT id, cost, image, number_of_persons, name, description, empty_rooms_amount FROM (\n" +
            "SELECT rooms.id, cost, image, number_of_persons, t.name, description, (CASE WHEN reserved_rooms_amount IS NULL THEN rooms_amount ELSE rooms_amount - reserved_rooms_amount END) AS empty_rooms_amount FROM (\n" +
            "SELECT rooms_id, sum(rooms_amount) AS reserved_rooms_amount  FROM users_has_rooms\n" +
            "WHERE arrival<=@arrival AND departure>=@departure\n" +
            "     OR arrival>=@arrival AND arrival<=@departure\n" +
            "     OR departure>=@arrival AND departure<=@departure\n" +
            "GROUP BY rooms_id) AS reserved_rooms\n" +
            "  RIGHT JOIN rooms ON reserved_rooms.rooms_id = rooms.id\n" +
            "  CROSS JOIN rooms_translate t ON rooms.id = t.rooms_id\n" +
            "  CROSS JOIN language l ON t.language_id = l.id\n" +
            "WHERE rooms.visibility>'0' AND l.name=?) AS table_without_invisible WHERE empty_rooms_amount>'0'";
    private final static String SAVE_POINT_NAME = "savePoint";
    private final static String SQL_ROOM_PAYMENT_BY_ID = "UPDATE users, users_has_rooms, rooms\n" +
            "SET balance=ROUND(balance-rooms.cost*users_has_rooms.rooms_amount, 2), paid='1'\n" +
            "WHERE users_has_rooms.id=?\n" +
            "      AND paid=0\n" +
            "      AND balance>=rooms.cost*users_has_rooms.rooms_amount\n" +
            "      AND users.id=users_has_rooms.users_id\n" +
            "      AND rooms.id=users_has_rooms.rooms_id";
    private final static String SQL_SET_APPROVAL = "UPDATE users_has_rooms SET approval='1' WHERE id=?";
    private final static String SQL_RETURN_MONEY = "UPDATE users_has_rooms, rooms, users\n" +
            "SET  balance=ROUND(balance+users_has_rooms.total_cost, 2), users_has_rooms.paid='0'\n" +
            "WHERE users_has_rooms.paid='1' AND users_has_rooms.id=? AND rooms.id=users_has_rooms.rooms_id AND users.id=users_has_rooms.users_id";
    private final static String SQL_GET_ALL_APPLICATIONS = "SELECT users_has_rooms.id, users_id, users_has_rooms.rooms_id, users_has_rooms.rooms_amount, arrival, departure,\n" +
            "  paid, total_cost, r.rooms_amount, translate.name, r.cost, image, translate.description, number_of_persons,\n" +
            "  (SELECT ceil(count(id)/?) FROM users_has_rooms WHERE approval='0'),\n" +
            " t.id, t.cost, t.visibility, tt.name, tt.description\n" +
            "FROM users_has_rooms\n" +
            "  INNER JOIN rooms r ON users_has_rooms.rooms_id = r.id\n" +
            "  INNER JOIN rooms_translate translate ON r.id = translate.rooms_id\n" +
            "  INNER JOIN tariffs t ON tariffs_id=t.id\n" +
            "  INNER JOIN tariffs_translate tt ON t.id = tt.tariffs_id\n" +
            "  INNER JOIN language l ON translate.language_id = l.id AND tt.language_id = l.id\n" +
            "WHERE approval='0' AND l.name=? AND canceled='0' LIMIT ? OFFSET ?";
    private final static String SQL_UPDATE_TOTAL_COST = "UPDATE users_has_rooms, users, rooms, tariffs\n" +
            "SET total_cost=round((rooms.cost + tariffs.cost)*users_has_rooms.rooms_amount*(1-users.discount/100)*(departure-arrival), 2)\n" +
            "WHERE users.id=users_has_rooms.users_id\n" +
            "      AND rooms.id=users_has_rooms.rooms_id\n" +
            "      AND users_has_rooms.id=LAST_INSERT_ID()\n" +
            "      AND tariffs_id=tariffs.id";
    private final static String SQL_GET_ALL_ROOM_TARIFFS = "SELECT tariffs.id, t2.name, t2.description, cost, visibility FROM tariffs\n" +
            "  INNER JOIN tariffs_translate t2 ON tariffs.id = t2.tariffs_id\n" +
            "  INNER JOIN language l ON t2.language_id = l.id\n" +
            "WHERE l.name=?";
    private final static String SQL_GET_ALL_VISIBLE_ROOMS = "SELECT tariffs.id, t2.name, t2.description, cost FROM tariffs\n" +
            "  INNER JOIN tariffs_translate t2 ON tariffs.id = t2.tariffs_id\n" +
            "  INNER JOIN language l ON t2.language_id = l.id\n" +
            "WHERE l.name=? AND tariffs.visibility='1'";
    private final static String SQL_INSERT_TARIFF = "INSERT INTO tariffs (cost, visibility)\n" +
            "    VALUES (?, ?)";
    private final static String SQL_INSERT_TARIFF_TRANSLATE = "INSERT INTO tariffs_translate (tariffs_id, language_id, name, description)\n" +
            "    VALUES (last_insert_id(), (SELECT id FROM language WHERE language.name=?), ?, ?)";
    private final static String SQL_UPDATE_TARIFF = "UPDATE tariffs SET cost=?, visibility=? WHERE id=?";
    private final static String SQL_UPDATE_TARIFF_TRANSLATE = "UPDATE tariffs_translate SET name=?, description=? WHERE language_id=(SELECT id FROM language WHERE language.name=?) AND tariffs_id=?";
    private final static String SQL_GET_EXTENDED_TARIFF = "SELECT cost, visibility, l.name, t2.name, t2.description FROM tariffs\n" +
            "  INNER JOIN tariffs_translate t2 ON tariffs.id = t2.tariffs_id\n" +
            "  INNER JOIN language l ON t2.language_id = l.id\n" +
            "WHERE tariffs.id=?";
    private final static String SQL_GET_USER_HISTORY_PAGE = "SELECT users_has_rooms.id, users_has_rooms.rooms_amount, r.id, arrival, departure, r.cost, image, number_of_persons, translate.name, translate.description, users_has_rooms.approval, users_has_rooms.paid, total_cost, canceled, t.id, t.cost, t.visibility, tt.name, tt.description,\n" +
            "  (SELECT ceil(count(id)/?) FROM users_has_rooms WHERE users_id=?)\n" +
            "FROM users_has_rooms\n" +
            "  INNER JOIN rooms r ON users_has_rooms.rooms_id = r.id\n" +
            "  INNER JOIN rooms_translate translate ON r.id = translate.rooms_id\n" +
            "  INNER JOIN tariffs t ON tariffs_id=t.id\n" +
            "  INNER JOIN tariffs_translate tt ON t.id = tt.tariffs_id\n" +
            "  INNER JOIN language l ON translate.language_id = l.id AND tt.language_id = l.id\n" +
            "WHERE l.name=? AND users_id=? ORDER BY users_has_rooms.id DESC LIMIT ? OFFSET ?";
    private final static String SQL_GET_THE_AMOUNT_OF_CANCELLATIONS = "SELECT count(id) FROM users_has_rooms WHERE canceled='1' AND users_id=?";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private int pageLength;
    private SimpleDateFormat format;
    private int numberOfCanceled;

    /**
     * Constructor without params.
     */
    public RoomDAOImpl() {
        DAOParameterManager manager = DAOParameterManager.getInstance();
        String dateTemplate = manager.getValue(DAOParameter.DAO_DATE_TEMPLATE);
        format = new SimpleDateFormat(dateTemplate);
        pageLength = Integer.parseInt(manager.getValue(DAOParameter.DAO_ROOM_PAGE_LENGTH));
        numberOfCanceled = Integer.parseInt(manager.getValue(DAOParameter.DAO_ROOM_NUMBER_OF_CANCELED));
    }

    /**
     * Add new room in database.
     *
     * @param room object stores all the information about the type of room
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see ExtendedRoom
     */
    @Override
    public void addRoom(ExtendedRoom room) throws DAOException {
        Connection connection = null;
        PreparedStatement roomStatement = null;
        PreparedStatement roomTranslateStatement = null;
        Savepoint savepoint = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            savepoint = connection.setSavepoint(SAVE_POINT_NAME);

            roomStatement = connection.prepareStatement(SQL_INSERT_ROOM);
            roomStatement.setInt(1, room.getRoomsAmount());
            roomStatement.setDouble(2, room.getCost());
            roomStatement.setString(3, room.getImage());
            roomStatement.setBoolean(4, room.isVisibility());
            roomStatement.setInt(5, room.getNumberOfPersons());

            roomStatement.execute();

            roomTranslateStatement = connection.prepareStatement(SQL_INSERT_ROOM_TRANSLATE);
            for (LanguageEnum lang : LanguageEnum.values()) {
                roomTranslateStatement.setString(1, lang.getName());
                roomTranslateStatement.setString(2, room.getName().get(lang));
                roomTranslateStatement.setString(3, room.getDescription().get(lang));
                roomTranslateStatement.addBatch();
            }
            roomTranslateStatement.executeBatch();
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (savepoint != null) {
                    connection.rollback(savepoint);
                }
            } catch (SQLException e1) {
                log.warn("Rollback failed.", e1);
            }
            log.warn("The method was not completed.", e);
            throw new DAOException("Method addRoom(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, roomStatement, roomTranslateStatement);
        }
    }

    /**
     * Produce room by database.
     *
     * @param roomId   id of produced room.
     * @param language for get information required language.
     * @return instance of Room
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see LanguageEnum
     * @see Room
     */
    @Override
    public Room getRoom(int roomId, LanguageEnum language) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Room room = new Room();
        room.setId(roomId);
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_ROOM);
            statement.setString(1, language.getName());
            statement.setInt(2, roomId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int roomsAmount = resultSet.getInt(1);
                room.setRoomsAmount(roomsAmount);
                double cost = resultSet.getDouble(2);
                room.setCost(cost);
                String image = resultSet.getString(3);
                room.setImage(image);
                String name = resultSet.getString(4);
                room.setName(name);
                String description = resultSet.getString(5);
                room.setDescription(description);
                int numberOfPersons = resultSet.getInt(6);
                room.setNumberOfPersons(numberOfPersons);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getRoom(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return room;
    }

    /**
     * Produce Room with all information about required room.
     *
     * @param roomId id of produced room.
     * @return instance of ExtendedRoom.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see ExtendedRoom
     */
    @Override
    public ExtendedRoom getExtendedRoom(int roomId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExtendedRoom room = new ExtendedRoom();
        room.setId(roomId);
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_EXTENDED_ROOM);
            statement.setInt(1, roomId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int roomsAmount = resultSet.getInt(1);
                room.setRoomsAmount(roomsAmount);
                double cost = resultSet.getDouble(2);
                room.setCost(cost);
                String image = resultSet.getString(3);
                room.setImage(image);
                boolean visibility = (resultSet.getByte(4) > 0);
                room.setVisibility(visibility);
                int numberOfPersons = resultSet.getInt(8);
                room.setNumberOfPersons(numberOfPersons);
                Map<LanguageEnum, String> nameMap = new EnumMap<>(LanguageEnum.class);
                String langName = resultSet.getString(7);
                LanguageEnum lang = LanguageEnum.valueOf(langName.toUpperCase());
                String roomName = resultSet.getString(5);
                nameMap.put(lang, roomName);
                Map<LanguageEnum, String> descriptionMap = new EnumMap<>(LanguageEnum.class);
                String description = resultSet.getString(6);
                descriptionMap.put(lang, description);
                while (resultSet.next()) {
                    langName = resultSet.getString(7);
                    lang = LanguageEnum.valueOf(langName.toUpperCase());
                    roomName = resultSet.getString(5);
                    nameMap.put(lang, roomName);
                    description = resultSet.getString(6);
                    descriptionMap.put(lang, description);
                }
                room.setName(nameMap);
                room.setDescription(descriptionMap);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getExtendedRoom(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return room;
    }

    /**
     * Produce list of rooms, what is empty if required range of date.
     *
     * @param arrival   Date
     * @param departure Date
     * @param language  for contant
     * @return list of Rooms
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see LanguageEnum
     * @see Room
     */
    @Override
    public List<Room> getAllEmptyVisibleRoomsInRange(Date arrival, Date departure, LanguageEnum language) throws DAOException {
        Connection connection = null;
        PreparedStatement setRangeStatement = null;
        PreparedStatement roomsStatement = null;
        ResultSet resultSet = null;
        List<Room> rooms = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            setRangeStatement = connection.prepareStatement(SQL_SET_DATE_RANGE);
            setRangeStatement.setDate(1, new java.sql.Date(arrival.getTime()));
            setRangeStatement.setDate(2, new java.sql.Date(departure.getTime()));
            setRangeStatement.execute();
            roomsStatement = connection.prepareStatement(SQL_GET_ALL_EMPTY_ROOMS);
            roomsStatement.setString(1, language.getName());
            resultSet = roomsStatement.executeQuery();
            while (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getInt(1));
                room.setRoomsAmount(resultSet.getInt(7));
                room.setCost(resultSet.getDouble(2));
                room.setDescription(resultSet.getString(6));
                room.setName(resultSet.getString(5));
                room.setImage(resultSet.getString(3));
                room.setNumberOfPersons(resultSet.getInt(4));
                rooms.add(room);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getAllEmptyVisibleRoomsInRange(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, resultSet, setRangeStatement, roomsStatement);
        }
        return rooms;
    }

    /**
     * Edit required room in database.
     *
     * @param editedRoom object stores all the information about the type of room.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see ExtendedRoom
     */
    @Override
    public void editRoom(ExtendedRoom editedRoom) throws DAOException {
        Connection connection = null;
        PreparedStatement roomStatement = null;
        PreparedStatement roomTranslateStatement = null;
        Savepoint savepoint = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            savepoint = connection.setSavepoint(SAVE_POINT_NAME);

            roomStatement = connection.prepareStatement(SQL_UPDATE_ROOMS);
            roomStatement.setInt(1, editedRoom.getRoomsAmount());
            roomStatement.setDouble(2, editedRoom.getCost());
            roomStatement.setString(3, editedRoom.getImage());
            roomStatement.setBoolean(4, editedRoom.isVisibility());
            roomStatement.setInt(5, editedRoom.getNumberOfPersons());
            roomStatement.setInt(6, editedRoom.getId());

            roomStatement.executeUpdate();

            roomTranslateStatement = connection.prepareStatement(SQL_UPDATE_ROOMS_TRANSLATE);
            for (LanguageEnum lang : LanguageEnum.values()) {
                roomTranslateStatement.setString(1, editedRoom.getName().get(lang));
                roomTranslateStatement.setString(2, editedRoom.getDescription().get(lang));
                roomTranslateStatement.setInt(3, editedRoom.getId());
                roomTranslateStatement.setString(4, lang.getName());
                roomTranslateStatement.addBatch();
            }
            roomTranslateStatement.executeBatch();
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (savepoint != null) {
                    connection.rollback(savepoint);
                }
            } catch (SQLException e1) {
                log.warn("Rollback failed.", e1);
            }
            log.warn("The method was not completed.", e);
            throw new DAOException("Method editRoom(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, roomStatement, roomTranslateStatement);
        }
    }

    /**
     * Produce list of Rooms by visible rooms.
     *
     * @param language for get content of different languages.
     * @return list of Rooms
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see LanguageEnum
     * @see Room
     */
    @Override
    public List<Room> getVisibleRooms(LanguageEnum language) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Room> rooms = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_VISIBLE_ROOMS);
            preparedStatement.setString(1, language.getName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int roomId = resultSet.getInt(1);
                int roomsAmount = resultSet.getInt(2);
                double cost = resultSet.getDouble(3);
                String image = resultSet.getString(4);
                String name = resultSet.getString(5);
                String description = resultSet.getString(6);
                int numberOfPersons = resultSet.getInt(7);
                Room room = new Room(roomId, roomsAmount, name, cost, image, description, numberOfPersons);
                rooms.add(room);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getVisibleRooms(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return rooms;
    }

    /**
     * Using for show visible and invisible rooms for{@code moderator } and {@code administrator}.
     *
     * @param language language of produced list.
     * @return list of rooms.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see LanguageEnum
     * @see Room
     */
    @Override
    public List<Room> getAllRoomTypes(LanguageEnum language) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Room> rooms = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GET_ALL_ROOMS);
            preparedStatement.setString(1, language.getName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getInt(1));
                room.setRoomsAmount(resultSet.getInt(2));
                room.setCost(resultSet.getDouble(3));
                room.setDescription(resultSet.getString(6));
                room.setName(resultSet.getString(5));
                room.setImage(resultSet.getString(4));
                room.setNumberOfPersons(7);
                rooms.add(room);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getAllRoomTypes(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return rooms;
    }

    /**
     * User get room.
     *
     * @param room reserved room.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see ReservedRoom
     */
    @Override
    public void userGetRooms(ReservedRoom room) throws DAOException {
        Connection connection = null;
        PreparedStatement usersHasRoomsStatement = null;
        Statement updateStatement = null;
        Savepoint savepoint = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint(SAVE_POINT_NAME);

            usersHasRoomsStatement = connection.prepareStatement(SQL_ADD_ENTRY_IN_USERS_HAS_ROOMS);
            usersHasRoomsStatement.setInt(1, room.getUserId());
            usersHasRoomsStatement.setInt(2, room.getRoomId());
            usersHasRoomsStatement.setInt(3, room.getRoomsAmount());
            DateRange range = room.getRange();
            usersHasRoomsStatement.setDate(4, new java.sql.Date(range.getArrival().getTime()));
            usersHasRoomsStatement.setDate(5, new java.sql.Date(range.getDeparture().getTime()));
            usersHasRoomsStatement.setInt(6, room.getTariffId());

            usersHasRoomsStatement.executeUpdate();

            updateStatement = connection.createStatement();

            updateStatement.executeUpdate(SQL_UPDATE_TOTAL_COST);

            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (savepoint != null) {
                    connection.rollback(savepoint);
                }
            } catch (SQLException e1) {
                log.warn("Rollback failed.", e1);
            }
            log.warn("The method was not completed.", e);
            throw new DAOException("Method userGetRooms(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, usersHasRoomsStatement, updateStatement);
        }
    }

    /**
     * User return room.
     *
     * @param reservedRoomId - id of transaction.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public void userReturnsRooms(int reservedRoomId) throws DAOException {
        Connection connection = null;
        PreparedStatement deleteEntryStatement = null;
        PreparedStatement returnMoneyStatement = null;
        Savepoint savepoint = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            savepoint = connection.setSavepoint(SAVE_POINT_NAME);

            returnMoneyStatement = connection.prepareStatement(SQL_RETURN_MONEY);
            returnMoneyStatement.setInt(1, reservedRoomId);
            returnMoneyStatement.executeUpdate();

            deleteEntryStatement = connection.prepareStatement(SQL_CANCEL_ENTRY_FROM_USERS_HAS_ROOMS_BY_ID);
            deleteEntryStatement.setInt(1, reservedRoomId);
            deleteEntryStatement.executeUpdate();

            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (savepoint != null) {
                    connection.rollback(savepoint);
                }
            } catch (SQLException e1) {
                log.warn("Rollback failed.", e1);
            }
            log.warn("The method was not completed.", e);
            throw new DAOException("Method userReturnsRooms(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, deleteEntryStatement, returnMoneyStatement);
        }
    }

    /**
     * Produce all purchase history of required user.
     *
     * @param userId     user ID.
     * @param pageNumber page of history
     * @param language   type of local language
     * @return items of purchase history
     * @throws DAOException wrap for SQLException, ConnectionPoolException and ParseException.
     * @see DAOException
     * @see LanguageEnum
     * @see UserRoom
     */
    @Override
    public UserHistoryPage getUserHistory(int userId, int pageNumber, LanguageEnum language) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserHistoryPage historyPage = new UserHistoryPage();
        List<UserRoom> roomHistory = new LinkedList<>();
        historyPage.setRoomHistory(roomHistory);
        historyPage.setCurrentPage(pageNumber);
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_USER_HISTORY_PAGE);
            statement.setInt(1, pageLength);
            statement.setInt(2, userId);
            statement.setString(3, language.getName());
            statement.setInt(4, userId);
            statement.setInt(5, pageLength);
            statement.setInt(6, pageLength * (pageNumber - 1));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int transactionId = resultSet.getInt(1);
                int roomsAmount = resultSet.getInt(2);
                int roomId = resultSet.getInt(3);
                Date arrival = format.parse(resultSet.getString(4));
                Date departure = format.parse(resultSet.getString(5));
                double cost = resultSet.getDouble(6);
                String image = resultSet.getString(7);
                int numberOfPersons = resultSet.getInt(8);
                String name = resultSet.getString(9);
                String description = resultSet.getString(10);
                Room room = new Room(roomId, roomsAmount, name, cost, image, description, numberOfPersons);
                boolean approval = resultSet.getInt(11) > 0;
                boolean paid = resultSet.getInt(12) > 0;
                double totalCost = resultSet.getDouble(13);
                boolean canceled = resultSet.getInt(14) > 0;
                int tariffId = resultSet.getInt(15);
                ReservedRoom reservedRoom = new ReservedRoom(transactionId, userId, roomId, roomsAmount, new DateRange(arrival, departure), approval, paid, totalCost, tariffId, canceled);
                double tariffCost = resultSet.getDouble(16);
                boolean tariffVisibility = resultSet.getInt(17) > 0;
                String tariffName = resultSet.getString(18);
                String tariffDescription = resultSet.getString(19);
                RoomTariff tariff = new RoomTariff(tariffId, tariffName, tariffDescription, tariffCost, tariffVisibility);
                UserRoom historyItem = new UserRoom(room, reservedRoom, tariff);
                roomHistory.add(historyItem);
                historyPage.setPageAmount(resultSet.getInt(20));
            }
        } catch (SQLException | ConnectionPoolException | ParseException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getUserHistory(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return historyPage;
    }

    /**
     * User pays for the room.
     *
     * @param transactionId id of payed transaction.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public void roomPayment(int transactionId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_ROOM_PAYMENT_BY_ID);
            statement.setInt(1, transactionId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method roomPayment(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement);
        }
    }

    /**
     * Set {@code approval} column.
     *
     * @param transactionId id of payed transaction.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public void setApproval(int transactionId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SET_APPROVAL);
            statement.setInt(1, transactionId);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method setApproval(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement);
        }
    }

    /**
     * The method looks for unapproved applications in the database and displays them in the pages
     *
     * @param pageNumber necessary page.
     * @param language   necessary language.
     * @return page with all not accepted applications.
     * @throws DAOException wrap for SQLException, ConnectionPoolException and ParseException.
     * @see DAOException
     * @see UserHistoryPage
     * @see LanguageEnum
     */
    @Override
    public UserHistoryPage getAllApplications(int pageNumber, LanguageEnum language) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserHistoryPage applicationPage = new UserHistoryPage();
        List<UserRoom> applications = new LinkedList<>();
        applicationPage.setRoomHistory(applications);
        applicationPage.setCurrentPage(pageNumber);
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_ALL_APPLICATIONS);
            statement.setInt(1, pageLength);
            statement.setString(2, language.getName());
            statement.setInt(3, pageLength);
            statement.setInt(4, pageLength * (pageNumber - 1));
            resultSet = statement.executeQuery();
            int pageAmount = 1;
            while (resultSet.next()) {
                int transactionId = resultSet.getInt(1);
                int userId = resultSet.getInt(2);
                int roomId = resultSet.getInt(3);
                int takenRoomsAmount = resultSet.getInt(4);
                Date arrival = format.parse(resultSet.getString(5));
                Date departure = format.parse(resultSet.getString(6));
                boolean paid = resultSet.getInt(7) > 0;
                double totalCost = resultSet.getDouble(8);
                boolean approval = false;
                int roomsAmount = resultSet.getInt(9);
                String name = resultSet.getString(10);
                double cost = resultSet.getDouble(11);
                String image = resultSet.getString(12);
                String description = resultSet.getString(13);
                int numberOfPersons = resultSet.getInt(14);
                pageAmount = resultSet.getInt(15);
                boolean canceled = false;
                int tariffId = resultSet.getInt(16);
                double tariffCost = resultSet.getDouble(17);
                boolean tariffVisibility = resultSet.getInt(18) > 0;
                String tariffName = resultSet.getString(19);
                String tariffDescription = resultSet.getString(20);
                ReservedRoom reservedRoom = new ReservedRoom(transactionId, userId, roomId, takenRoomsAmount, new DateRange(arrival, departure), approval, paid, totalCost, tariffId, canceled);
                Room room = new Room(roomId, roomsAmount, name, cost, image, description, numberOfPersons);
                RoomTariff tariff = new RoomTariff(tariffId, tariffName, tariffDescription, tariffCost, tariffVisibility);
                UserRoom userRoom = new UserRoom(room, reservedRoom, tariff);
                applications.add(userRoom);
            }
            applicationPage.setPageAmount(pageAmount);
        } catch (SQLException | ConnectionPoolException | ParseException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getRoom(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return applicationPage;
    }

    /**
     * Using for show visible and invisible tariffs for{@code moderator } and {@code administrator}.
     *
     * @param language language of produced list.
     * @return list of tariffs.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see LanguageEnum
     * @see RoomTariff
     */
    @Override
    public List<RoomTariff> getAllRoomTariffs(LanguageEnum language) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<RoomTariff> roomTariffs = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_ALL_ROOM_TARIFFS);
            statement.setString(1, language.getName());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int tariffId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                double cost = resultSet.getDouble(4);
                boolean visibility = resultSet.getInt(5) > 0;
                RoomTariff tariff = new RoomTariff(tariffId, name, description, cost, visibility);
                roomTariffs.add(tariff);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getAllRoomTariffs(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return roomTariffs;
    }

    /**
     * Produce list of RoomTariffs by visible tariffs.
     *
     * @param language for get content of different languages.
     * @return list of RoomTariffs
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see LanguageEnum
     * @see RoomTariff
     */
    @Override
    public List<RoomTariff> getAllVisibleRoomTariffs(LanguageEnum language) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<RoomTariff> roomTariffs = new LinkedList<>();
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_ALL_VISIBLE_ROOMS);
            statement.setString(1, language.getName());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int tariffId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                double cost = resultSet.getDouble(4);
                boolean visibility = true;
                RoomTariff tariff = new RoomTariff(tariffId, name, description, cost, visibility);
                roomTariffs.add(tariff);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getAllVisibleRoomTariffs(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return roomTariffs;
    }

    /**
     * Add new tariff in database.
     *
     * @param tariff object stores all the information about the tariff
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see ExtendedRoomTariff
     */
    @Override
    public void addRoomTariff(ExtendedRoomTariff tariff) throws DAOException {
        Connection connection = null;
        PreparedStatement tariffStatement = null;
        PreparedStatement tariffTranslateStatement = null;
        Savepoint savepoint = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            savepoint = connection.setSavepoint(SAVE_POINT_NAME);

            tariffStatement = connection.prepareStatement(SQL_INSERT_TARIFF);
            tariffStatement.setDouble(1, tariff.getCost());
            tariffStatement.setInt(2, (tariff.isVisibility() ? 1 : 0));

            tariffStatement.execute();

            tariffTranslateStatement = connection.prepareStatement(SQL_INSERT_TARIFF_TRANSLATE);
            for (LanguageEnum lang : LanguageEnum.values()) {
                tariffTranslateStatement.setString(1, lang.getName());
                tariffTranslateStatement.setString(2, tariff.getNames().get(lang));
                tariffTranslateStatement.setString(3, tariff.getDescriptions().get(lang));
                tariffTranslateStatement.addBatch();
            }
            tariffTranslateStatement.executeBatch();
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (savepoint != null) {
                    connection.rollback(savepoint);
                }
            } catch (SQLException e1) {
                log.warn("Rollback failed.", e1);
            }
            log.warn("The method was not completed.", e);
            throw new DAOException("Method addRoomTariff(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, tariffStatement, tariffTranslateStatement);
        }
    }

    /**
     * Edit required tariff in database.
     *
     * @param tariff object stores all the information about the tariff.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see ExtendedRoomTariff
     */
    @Override
    public void editTariff(ExtendedRoomTariff tariff) throws DAOException {
        Connection connection = null;
        PreparedStatement tariffStatement = null;
        PreparedStatement tariffTranslateStatement = null;
        Savepoint savepoint = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            savepoint = connection.setSavepoint(SAVE_POINT_NAME);

            tariffStatement = connection.prepareStatement(SQL_UPDATE_TARIFF);
            tariffStatement.setDouble(1, tariff.getCost());
            tariffStatement.setInt(2, (tariff.isVisibility() ? 1 : 0));
            tariffStatement.setInt(3, tariff.getId());

            tariffStatement.executeUpdate();

            tariffTranslateStatement = connection.prepareStatement(SQL_UPDATE_TARIFF_TRANSLATE);
            for (LanguageEnum lang : LanguageEnum.values()) {
                tariffTranslateStatement.setString(1, tariff.getNames().get(lang));
                tariffTranslateStatement.setString(2, tariff.getDescriptions().get(lang));
                tariffTranslateStatement.setString(3, lang.getName());
                tariffTranslateStatement.setInt(4, tariff.getId());
                tariffTranslateStatement.addBatch();
            }
            tariffTranslateStatement.executeBatch();
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (savepoint != null) {
                    connection.rollback(savepoint);
                }
            } catch (SQLException e1) {
                log.warn("Rollback failed.", e1);
            }
            log.warn("The method was not completed.", e);
            throw new DAOException("Method editTariff(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, tariffStatement, tariffTranslateStatement);
        }
    }

    /**
     * Produce object with all information about required tariff.
     *
     * @param tariffId id of produced tariff.
     * @return instance of ExtendedRoomTariff.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     * @see ExtendedRoomTariff
     */
    @Override
    public ExtendedRoomTariff getExtendedRoomTariff(int tariffId) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExtendedRoomTariff tariff = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_EXTENDED_TARIFF);
            statement.setInt(1, tariffId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double cost = resultSet.getDouble(1);
                boolean visibility = (resultSet.getByte(2) > 0);
                Map<LanguageEnum, String> nameMap = new EnumMap<>(LanguageEnum.class);
                String langName = resultSet.getString(3);
                LanguageEnum lang = LanguageEnum.valueOf(langName.toUpperCase());
                String tariffName = resultSet.getString(4);
                nameMap.put(lang, tariffName);
                Map<LanguageEnum, String> descriptionMap = new EnumMap<>(LanguageEnum.class);
                String tariffDescription = resultSet.getString(5);
                descriptionMap.put(lang, tariffDescription);
                while (resultSet.next()) {
                    langName = resultSet.getString(3);
                    lang = LanguageEnum.valueOf(langName.toUpperCase());
                    tariffName = resultSet.getString(4);
                    nameMap.put(lang, tariffName);
                    tariffDescription = resultSet.getString(5);
                    descriptionMap.put(lang, tariffDescription);
                }
                tariff = new ExtendedRoomTariff(tariffId, cost, visibility, nameMap, descriptionMap);
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method getExtendedRoomTariff(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, statement, resultSet);
        }
        return tariff;
    }

    /**
     * Checks user for {@code numberOfCanceled} undos.
     *
     * @param userId id of the user being checked
     * @return if user has {@code numberOfCanceled} undos (or more) return true, else false.
     * @throws DAOException wrap for SQLException, ConnectionPoolException.
     * @see DAOException
     */
    @Override
    public boolean checkCancelNumber(int userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean numberExceeded = false;
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(SQL_GET_THE_AMOUNT_OF_CANCELLATIONS);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberExceeded = resultSet.getInt(1) >= numberOfCanceled;
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.warn("The method was not completed.", e);
            throw new DAOException("Method checkCancelNumber(...) failed.", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return numberExceeded;
    }

}
