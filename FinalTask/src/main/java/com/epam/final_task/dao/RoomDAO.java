package com.epam.final_task.dao;

import com.epam.final_task.exception.DAOException;
import com.epam.final_task.entity.*;

import java.util.Date;
import java.util.List;

public interface RoomDAO {

    void addRoom(ExtendedRoom room) throws DAOException;

    Room getRoom(int roomId, LanguageEnum language) throws DAOException;

    ExtendedRoom getExtendedRoom(int roomId) throws DAOException;

    List<Room> getAllEmptyVisibleRoomsInRange(Date arrival, Date departure, LanguageEnum language) throws DAOException;

    default List<Room> getAllEmptyVisibleRoomsInRange(DateRange range, LanguageEnum language) throws DAOException {
        return getAllEmptyVisibleRoomsInRange(range.getArrival(), range.getDeparture(), language);
    }

    List<Room> getVisibleRooms(LanguageEnum language) throws DAOException;

    List<Room> getAllRoomTypes(LanguageEnum language) throws DAOException;

    void editRoom(ExtendedRoom editedRoom) throws DAOException;

    void userGetRooms(ReservedRoom room) throws DAOException;

    void userReturnsRooms(int reservedRoomId) throws DAOException;

    UserHistoryPage getUserHistory(int userId, int pageNumber, LanguageEnum language) throws DAOException;

    void roomPayment(int transactionId) throws DAOException;

    void setApproval(int transactionId) throws DAOException;

    UserHistoryPage getAllApplications(int pageNumber, LanguageEnum language) throws DAOException;

    List<RoomTariff> getAllRoomTariffs(LanguageEnum language) throws DAOException;

    List<RoomTariff> getAllVisibleRoomTariffs(LanguageEnum language) throws DAOException;

    void addRoomTariff(ExtendedRoomTariff tariff) throws DAOException;

    void editTariff(ExtendedRoomTariff tariff) throws DAOException;

    ExtendedRoomTariff getExtendedRoomTariff(int tariffId) throws DAOException;

    boolean checkCancelNumber(int userId) throws DAOException;

}
