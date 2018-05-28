package com.epam.final_task.service;

import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.entity.*;

import java.util.Date;
import java.util.List;

public interface RoomService {

    void addRoom(ExtendedRoom room) throws ServiceException;

    Room getRoom(int roomId, LanguageEnum language) throws ServiceException;

    ExtendedRoom getExtendedRoom(int roomId) throws ServiceException;

    List<Room> getAllEmptyVisibleRoomsInRange(Date arrival, Date departure, LanguageEnum language) throws ServiceException;

    default List<Room> getAllEmptyVisibleRoomsInRange(DateRange range, LanguageEnum language) throws ServiceException {
        return getAllEmptyVisibleRoomsInRange(range.getArrival(), range.getDeparture(), language);
    }

    List<Room> getVisibleRooms(LanguageEnum language) throws ServiceException;

    List<Room> getAllRoomTypes(LanguageEnum language) throws ServiceException;

    void editRoom(ExtendedRoom editedRoom) throws ServiceException;

    void userGetRooms(ReservedRoom room) throws ServiceException;

    void userReturnsRooms(int reservedRoomId) throws ServiceException;

    UserHistoryPage getUserHistory(int userId, int pageNumber, LanguageEnum language) throws ServiceException;

    void roomPayment(int transactionId) throws ServiceException;

    void setApproval(int transactionId) throws ServiceException;

    UserHistoryPage getAllApplications(int pageNumber, LanguageEnum language) throws ServiceException;

    List<RoomTariff> getAllRoomTariffs(LanguageEnum language) throws ServiceException;

    List<RoomTariff> getAllVisibleRoomTariffs(LanguageEnum language) throws ServiceException;

    void addRoomTariff(ExtendedRoomTariff tariff) throws ServiceException;

    void editTariff(ExtendedRoomTariff tariff) throws ServiceException;

    ExtendedRoomTariff getExtendedRoomTariff(int tariffId) throws ServiceException;

    boolean checkCancelNumber(int userId) throws ServiceException;

}
