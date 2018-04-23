package com.epam.final_task.DAO.impl;

import com.epam.final_task.DAO.RoomDAO;
import com.epam.final_task.DAO.connection_pool.ConnectionPool;
import com.epam.final_task.Exception.ConnectionPoolException;
import com.epam.final_task.entity.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class RoomDAOImpl implements RoomDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String SQL_GET_ALL_ROOMS = "SELECT rooms.id, rooms_amount, cost, image, t.name, t.description FROM hosteldb.rooms\n" +
            "  CROSS JOIN rooms_translate t ON rooms.id = t.rooms_id\n" +
            "  CROSS JOIN language l ON t.language_id = l.id WHERE l.name=?";

    private final static String SQL_GET_VISIBLE_ROOMS = "SELECT r.id, rooms_amount, cost, image, visibility, t.name, t.description, l.name FROM hosteldb.rooms AS r\n" +
            "  CROSS JOIN rooms_translate t ON r.id = t.rooms_id\n" +
            "  CROSS JOIN language l ON t.language_id = l.id WHERE l.name=? AND r.visibility>'0'";

    private final static String SQL_HIDE_ROOM = "UPDATE rooms SET visibility='0' WHERE id=?";

    private final static String SQL_VISION_ROOM = "UPDATE rooms SET visibility='1' WHERE id=?";

    private final static String SQL_INSERT_ROOM = "INSERT INTO rooms (rooms.id, rooms_amount, cost, image, visibility)\n" +
            "VALUES (null, ?, ?, ?, ?)";

    private final static String SQL_INSERT_ROOM_TRANSLATE = "INSERT INTO rooms_translate (rooms_id, language_id, name, description)\n" +
            "VALUES (LAST_INSERT_ID(), (SELECT id FROM language WHERE language.name=?), ?, ?)";

    private final static String SQL_GET_ROOM = "SELECT rooms_amount, cost, image, name, description FROM (SELECT r.id, rooms_amount, cost, image, visibility, t.name, t.description FROM hosteldb.rooms AS r\n" +
            "  CROSS JOIN rooms_translate t ON r.id = t.rooms_id\n" +
            "  CROSS JOIN language l ON t.language_id = l.id WHERE l.name=?) AS subselect WHERE id=? AND visibility='1'";

    private final static String SQL_GET_EXTENDED_ROOM = "SELECT r.id, rooms_amount, cost, image, visibility, t.name, t.description, l.name FROM hosteldb.rooms AS r\n" +
            "  CROSS JOIN rooms_translate t ON r.id = t.rooms_id\n" +
            "  CROSS JOIN language l ON t.language_id = l.id WHERE r.id=?";

    private final static String SQL_GET_USER_RESERVED_ROOMS = "SELECT * FROM users_has_rooms WHERE users_id=?";

    private final static String SQL_TAKE_ROOM_AMOUNT_FROM_ROOMS = "UPDATE rooms SET rooms_amount=rooms_amount-abs(?) WHERE id=?";

    private final static String SQL_ADD_ENTRY_IN_USERS_HAS_ROOMS = "INSERT INTO users_has_rooms (users_id, rooms_id, rooms_amount, arrival, departure)\n" +
            "VALUES (?, ?, ?, ?, ?)";

    private final static String SQL_GET_ROOMS_BY_ID_FROM_USERS_HAS_ROOMS = "SELECT rooms_id, rooms_amount FROM users_has_rooms WHERE id=?";

    private final static String SQL_ADD_ROOMS_AMOUNT_IN_ROOMS = "UPDATE rooms SET rooms_amount=rooms_amount+abs(?) WHERE id=?";

    private final static String SQL_DELETE_ENTRY_FROM_USERS_HAS_ROOMS_BY_ID = "DELETE FROM users_has_rooms WHERE id=?";

    @Override
    public void addRoom(ExtendedRoom room) {
        Connection connection = null;
        PreparedStatement roomStatement = null;
        PreparedStatement roomTranslateStatement = null;
        Savepoint savepoint = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            roomStatement = connection.prepareStatement(SQL_INSERT_ROOM);
            roomStatement.setInt(1, room.getRoomsAmount());
            roomStatement.setDouble(2, room.getCost());
            roomStatement.setString(3, room.getImage());
            roomStatement.setBoolean(4, room.isVisibility());

            savepoint = connection.setSavepoint("NewEmp");

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
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                roomStatement.close();
                roomTranslateStatement.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
    }

    @Override
    public Room getRoom(int roomId, LanguageEnum language) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Room room = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_ROOM);
            statement.setString(1, language.getName());
            statement.setInt(2, roomId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int roomsAmount = resultSet.getInt("rooms_amount");
                String name = resultSet.getString("name");
                double cost = resultSet.getDouble("cost");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                room = new Room(roomId, roomsAmount, name, cost, image, description);
            }
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
        return room;
    }

    @Override
    public ExtendedRoom getExtendedRoom(int roomId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExtendedRoom room = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_EXTENDED_ROOM);
            statement.setInt(1, roomId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int roomsAmount = resultSet.getInt("rooms_amount");
                double cost = resultSet.getDouble("cost");
                String image = resultSet.getString("image");
                boolean visibility = (resultSet.getByte("visibility") > 0) ? true : false;
                Map<LanguageEnum, String> nameMap = new EnumMap<>(LanguageEnum.class);
                String langName = resultSet.getString("l.name");
                LanguageEnum lang = LanguageEnum.valueOf(langName.toUpperCase());
                String roomName = resultSet.getString("t.name");
                nameMap.put(lang, roomName);
                Map<LanguageEnum, String> descriptionMap = new EnumMap<>(LanguageEnum.class);
                String description = resultSet.getString("description");
                descriptionMap.put(lang, description);
                while (resultSet.next()) {
                    langName = resultSet.getString("l.name");
                    lang = LanguageEnum.valueOf(langName.toUpperCase());
                    roomName = resultSet.getString("t.name");
                    nameMap.put(lang, roomName);
                    description = resultSet.getString("description");
                    descriptionMap.put(lang, description);
                }
                room = new ExtendedRoom(roomId, roomsAmount, nameMap, cost, image, descriptionMap, visibility);
            }
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
        return room;
    }

    @Override
    public void visionRoom(int roomId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_VISION_ROOM);
            preparedStatement.setInt(1, roomId);
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
    }

    @Override
    public List<Room> getEmptyRoomsInRange(Date arrival, Date departure, LanguageEnum language) {
        return null;
    }

    @Override
    public void hideRoom(int roomId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SQL_HIDE_ROOM);
            preparedStatement.setInt(1, roomId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            //log
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
    public void removeRoom(int roomId) {

    }

    @Override
    public List<Room> getVisibleRooms(LanguageEnum language) {
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
                int roomId = resultSet.getInt("id");
                int roomsAmount = resultSet.getInt("rooms_amount");
                double cost = resultSet.getDouble("cost");
                String description = resultSet.getString("description");
                String name = resultSet.getString("name");
                String image = resultSet.getString("image");
                Room room = new Room(roomId, roomsAmount, name, cost, image, description);
                rooms.add(room);
            }
        } catch (SQLException | ConnectionPoolException e) {
            //log
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
        return rooms;
    }

    @Override
    public void editRoom(int roomId, ExtendedRoom newRoom) {

    }

    @Override
    public List<ReservedRoom> getUserRooms(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ReservedRoom> rooms = new ArrayList<>();
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_USER_RESERVED_ROOMS);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //это надо фиксить (формат вынести)
            while (resultSet.next()) {
                int reservedRoomId = resultSet.getInt("id");
                int roomId = resultSet.getInt("rooms_id");
                int roomsAmount = resultSet.getInt("rooms_amount");
                Date arrival = dateFormat.parse(resultSet.getString("arrival"));
                Date departure = dateFormat.parse(resultSet.getString("departure"));
                DateRange range = new DateRange(arrival, departure);
                ReservedRoom room = new ReservedRoom(reservedRoomId, userId, roomId, roomsAmount, range);
                rooms.add(room);
            }
        } catch (SQLException | ConnectionPoolException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
        return rooms;
    }

    @Override
    public List<Room> getAllRooms(LanguageEnum language) {
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
                room.setId(resultSet.getInt("id"));
                room.setRoomsAmount(resultSet.getInt("rooms_amount"));
                room.setCost(resultSet.getDouble("cost"));
                room.setDescription(resultSet.getString("description"));
                room.setName(resultSet.getString("name"));
                room.setImage(resultSet.getString("image"));
                rooms.add(room);
            }
        } catch (SQLException | ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
        return rooms;
    }

    @Override
    public void userGetRooms(ReservedRoom room) {
        Connection connection = null;
        PreparedStatement roomsStatement = null;
        PreparedStatement usersHasRoomsStatement = null;
        Savepoint savepoint = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            roomsStatement = connection.prepareStatement(SQL_TAKE_ROOM_AMOUNT_FROM_ROOMS);
            roomsStatement.setInt(1, room.getRoomsAmount());
            roomsStatement.setInt(2, room.getRoomId());

            savepoint = connection.setSavepoint("NewEmp2");

            roomsStatement.execute();

            usersHasRoomsStatement = connection.prepareStatement(SQL_ADD_ENTRY_IN_USERS_HAS_ROOMS);
            usersHasRoomsStatement.setInt(1, room.getUserId());
            usersHasRoomsStatement.setInt(2, room.getRoomId());
            usersHasRoomsStatement.setInt(3, room.getRoomsAmount());
            DateRange range = room.getRange();
            usersHasRoomsStatement.setDate(4, new java.sql.Date(range.getArrival().getTime()));
            usersHasRoomsStatement.setDate(5, new java.sql.Date(range.getDeparture().getTime()));


            usersHasRoomsStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                roomsStatement.close();
                usersHasRoomsStatement.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
    }

    @Override
    public void userReturnsRooms(int reservedRoomId) {

        Connection connection = null;
        PreparedStatement roomsStatement = null;
        ResultSet resultSet = null;
        PreparedStatement updateRoomsStatement = null;
        PreparedStatement deleteEntryStatement = null;
        Savepoint savepoint = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            roomsStatement = connection.prepareStatement(SQL_GET_ROOMS_BY_ID_FROM_USERS_HAS_ROOMS);
            roomsStatement.setInt(1, reservedRoomId);
            resultSet = roomsStatement.executeQuery();
            if (!resultSet.next()) {
                throw new SQLException("null pointer exception");
            }
            int roomsId = resultSet.getInt("rooms_id");
            int roomsAmount = resultSet.getInt("rooms_amount");
            savepoint = connection.setSavepoint("NewEmp3");

            updateRoomsStatement = connection.prepareStatement(SQL_ADD_ROOMS_AMOUNT_IN_ROOMS);
            updateRoomsStatement.setInt(1, roomsAmount);
            updateRoomsStatement.setInt(2, roomsId);

            updateRoomsStatement.executeUpdate();

            deleteEntryStatement = connection.prepareStatement(SQL_DELETE_ENTRY_FROM_USERS_HAS_ROOMS_BY_ID);
            deleteEntryStatement.setInt(1, reservedRoomId);

            deleteEntryStatement.executeUpdate();

            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                roomsStatement.close();
                updateRoomsStatement.close();
                deleteEntryStatement.close();
                resultSet.close();
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws ParseException {

        RoomDAO roomDAO = new RoomDAOImpl();
        roomDAO.userReturnsRooms(2);

    }

}
