package com.epam.final_task.service.impl;

import com.epam.final_task.dao.impl.RoomDAOImpl;
import com.epam.final_task.entity.*;
import com.epam.final_task.exception.DAOException;
import com.epam.final_task.exception.ServiceException;
import com.epam.final_task.dao.DAOFactory;
import com.epam.final_task.dao.RoomDAO;
import com.epam.final_task.service.RoomService;
import com.epam.final_task.service.validation.RoomServiceValidator;

import java.util.Date;
import java.util.List;

public class RoomServiceImpl implements RoomService {

    @Override
    public void addRoom(ExtendedRoom room) throws ServiceException {
        if (!RoomServiceValidator.extendedRoomValidation(room)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        try {
            roomDAO.addRoom(room);
        } catch (DAOException e) {
            throw new ServiceException("addRoom(...) failed.", e);
        }
    }

    @Override
    public Room getRoom(int roomId, LanguageEnum language) throws ServiceException {
        if (!RoomServiceValidator.idValidation(roomId)) {
            return null;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        Room room = null;
        try {
            room = roomDAO.getRoom(roomId, language);
        } catch (DAOException e) {
            throw new ServiceException("getRoom(...) failed.", e);
        }
        return room;
    }

    @Override
    public ExtendedRoom getExtendedRoom(int roomId) throws ServiceException {
        if (!RoomServiceValidator.idValidation(roomId)) {
            return null;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        ExtendedRoom room;
        try {
            room = roomDAO.getExtendedRoom(roomId);
        } catch (DAOException e) {
            throw new ServiceException("getExtendedRoom(...) failed.", e);
        }
        return room;
    }

    @Override
    public List<Room> getAllEmptyVisibleRoomsInRange(Date arrival, Date departure, LanguageEnum language) throws ServiceException {
        if (!RoomServiceValidator.dateRangeValidation(arrival, departure)) {
            return null;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        List<Room> rooms;
        try {
            rooms = roomDAO.getAllEmptyVisibleRoomsInRange(arrival, departure, language);
        } catch (DAOException e) {
            throw new ServiceException("getAllEmptyVisibleRoomsInRange(...) failed.", e);
        }
        return rooms;
    }

    @Override
    public List<Room> getVisibleRooms(LanguageEnum language) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        List<Room> rooms;
        try {
            rooms = roomDAO.getVisibleRooms(language);
        } catch (DAOException e) {
            throw new ServiceException("getVisibleRooms(...) failed.", e);
        }
        return rooms;
    }

    @Override
    public List<Room> getAllRoomTypes(LanguageEnum language) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        List<Room> rooms;
        try {
            rooms = roomDAO.getAllRoomTypes(language);
        } catch (DAOException e) {
            throw new ServiceException("getAllRoomTypes(...) failed.", e);
        }
        return rooms;
    }

    @Override
    public void editRoom(ExtendedRoom editedRoom) throws ServiceException {
        if (!RoomServiceValidator.extendedRoomValidation(editedRoom)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        try {
            roomDAO.editRoom(editedRoom);
        } catch (DAOException e) {
            throw new ServiceException("editRoom(...) failed.", e);
        }
    }

    @Override
    public void userGetRooms(ReservedRoom reservedRoom) throws ServiceException {
        if (!RoomServiceValidator.reservedRoomValidation(reservedRoom)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        try {
            roomDAO.userGetRooms(reservedRoom);
        } catch (DAOException e) {
            throw new ServiceException("userGetRooms(...) failed.", e);
        }
    }

    @Override
    public void userReturnsRooms(int reservedRoomId) throws ServiceException {
        if (!RoomServiceValidator.reservedRoomIdValidation(reservedRoomId)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        try {
            roomDAO.userReturnsRooms(reservedRoomId);
        } catch (DAOException e) {
            throw new ServiceException("userReturnsRooms(...) failed.", e);
        }
    }

    @Override
    public UserHistoryPage getUserHistory(int userId, int pageNumber, LanguageEnum language) throws ServiceException {
        if (!RoomServiceValidator.idValidation(userId)) {
            return null;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        UserHistoryPage history = null;
        try {
            history = roomDAO.getUserHistory(userId, pageNumber, language);
        } catch (DAOException e) {
            throw new ServiceException("getUserHistory(...) failed.", e);
        }
        return history;
    }

    @Override
    public void roomPayment(int transactionId) throws ServiceException {
        if (!RoomServiceValidator.reservedRoomIdValidation(transactionId)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        try {
            roomDAO.roomPayment(transactionId);
        } catch (DAOException e) {
            throw new ServiceException("roomPayment(...) failed.", e);
        }
    }

    @Override
    public void setApproval(int transactionId) throws ServiceException {
        if (!RoomServiceValidator.reservedRoomIdValidation(transactionId)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        try {
            roomDAO.setApproval(transactionId);
        } catch (DAOException e) {
            throw new ServiceException("roomPayment(...) failed.", e);
        }
    }

    @Override
    public UserHistoryPage getAllApplications(int pageNumber, LanguageEnum language) throws ServiceException {
        if (!RoomServiceValidator.pageNumberValidation(pageNumber)) {
            return null;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        UserHistoryPage applicationPage = null;
        try {
            applicationPage = roomDAO.getAllApplications(pageNumber, language);
        } catch (DAOException e) {
            throw new ServiceException("getAllApplications(...) failed.", e);
        }
        return applicationPage;
    }

    @Override
    public List<RoomTariff> getAllRoomTariffs(LanguageEnum language) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        List<RoomTariff> tariffs = null;
        try {
            tariffs = roomDAO.getAllRoomTariffs(language);
        } catch (DAOException e) {
            throw new ServiceException("getAllRoomTariffs(...) failed.", e);
        }
        return tariffs;
    }

    @Override
    public List<RoomTariff> getAllVisibleRoomTariffs(LanguageEnum language) throws ServiceException {
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        List<RoomTariff> tariffs = null;
        try {
            tariffs = roomDAO.getAllVisibleRoomTariffs(language);
        } catch (DAOException e) {
            throw new ServiceException("getAllVisibleRoomTariffs(...) failed.", e);
        }
        return tariffs;
    }

    @Override
    public void addRoomTariff(ExtendedRoomTariff tariff) throws ServiceException {
        if (!RoomServiceValidator.extendedTariffValidation(tariff)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        try {
            roomDAO.addRoomTariff(tariff);
        } catch (DAOException e) {
            throw new ServiceException("addRoomTariff(...) failed.", e);
        }
    }

    @Override
    public void editTariff(ExtendedRoomTariff tariff) throws ServiceException {
        if (!RoomServiceValidator.extendedTariffValidation(tariff)) {
            return;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        try {
            roomDAO.editTariff(tariff);
        } catch (DAOException e) {
            throw new ServiceException("editTariff(...) failed.", e);
        }
    }

    @Override
    public ExtendedRoomTariff getExtendedRoomTariff(int tariffId) throws ServiceException {
        if (!RoomServiceValidator.idValidation(tariffId)) {
            return null;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        ExtendedRoomTariff tariff;
        try {
            tariff = roomDAO.getExtendedRoomTariff(tariffId);
        } catch (DAOException e) {
            throw new ServiceException("getExtendedRoomTariff(...) failed.", e);
        }
        return tariff;
    }

    @Override
    public boolean checkCancelNumber(int userId) throws ServiceException {
        if (!RoomServiceValidator.idValidation(userId)) {
            return false;
        }
        DAOFactory factory = DAOFactory.getInstance();
        RoomDAO roomDAO = factory.getRoomDAO();
        boolean numberExceeded = false;
        try {
            numberExceeded = roomDAO.checkCancelNumber(userId);
        } catch (DAOException e) {
            throw new ServiceException("checkCancelNumber(...) failed.", e);
        }
        return numberExceeded;
    }

}
