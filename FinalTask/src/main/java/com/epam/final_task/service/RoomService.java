package com.epam.final_task.service;

import com.epam.final_task.entity.*;

import java.util.Date;
import java.util.List;

public interface RoomService {

    void addRoom(ExtendedRoom room);

    Room getRoom(int roomId, LanguageEnum language);

    ExtendedRoom getExtendedRoom(int roomId);

    List<Room> getEmptyRoomsInRange(Date arrival, Date departure, LanguageEnum language);

    default List<Room> getEmptyRoomsInRange(DateRange range, LanguageEnum language) {
        return getEmptyRoomsInRange(range.getArrival(), range.getDeparture(), language);
    }

    void hideRoom(int roomId);

    void visionRoom(int roomId);

    void removeRoom(int roomId);

    List<Room> getVisibleRooms(LanguageEnum language);

    List<Room> getAllRooms(LanguageEnum language);

    void editRoom(int roomId, ExtendedRoom newRoom);

    void userGetRooms(ReservedRoom room);

    void userReturnsRooms(int reservedRoomId);

    List<ReservedRoom> getUserRooms(int id);

}
