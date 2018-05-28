package com.epam.final_task.dao.impl;

import com.epam.final_task.dao.RoomDAO;
import com.epam.final_task.entity.*;
import com.epam.final_task.exception.DAOException;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RoomDAOImplTest {

    @Test
    public void getRoom() {
        Room correctRoom = new Room();
        correctRoom.setId(1);
        correctRoom.setRoomsAmount(4);
        correctRoom.setCost(13.37);
        correctRoom.setImage("image/rooms/8");
        correctRoom.setName("Люкc");
        correctRoom.setDescription("Самый лучший люкс на свете.");
        correctRoom.setNumberOfPersons(1);

        Room incorrectRoom = new Room();
        int incorrectRoomId = 0;
        incorrectRoom.setId(incorrectRoomId);
        RoomDAO roomDAO = new RoomDAOImpl();
        try {
            Room correctRoomTest= roomDAO.getRoom(correctRoom.getId(), LanguageEnum.RU);
            assertTrue(correctRoom.equals(correctRoomTest));
            Room incorrectRoomTest = roomDAO.getRoom(incorrectRoomId, LanguageEnum.RU);
            assertTrue(incorrectRoomTest.equals(incorrectRoom));
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void getExtendedRoom() {
        ExtendedRoom room = new ExtendedRoom();
        int roomId = 1;
        room.setId(roomId);
        room.setCost(13.37);
        room.setRoomsAmount(4);
        room.setImage("image/rooms/8");
        room.setVisibility(true);
        room.setNumberOfPersons(1);

        Map<LanguageEnum, String> names = new HashMap<>();
        names.put(LanguageEnum.RU, "Люкc");
        names.put(LanguageEnum.EN, "Single");
        room.setName(names);

        Map<LanguageEnum, String> descriptions = new HashMap<>();
        descriptions.put(LanguageEnum.RU, "Самый лучший люкс на свете.");
        descriptions.put(LanguageEnum.EN, "The best single.");
        room.setDescription(descriptions);

        RoomDAO roomDAO = new RoomDAOImpl();
        try {
            ExtendedRoom testRoom = roomDAO.getExtendedRoom(roomId);
            assertTrue(room.equals(testRoom));
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void editRoom() {
        ExtendedRoom room = new ExtendedRoom();
        int roomId = 1;
        room.setId(roomId);
        room.setCost(131.37);
        room.setRoomsAmount(41);
        room.setImage("image/rooms/8.jpg1");
        room.setVisibility(true);
        room.setNumberOfPersons(1);

        Map<LanguageEnum, String> names = new HashMap<>();
        names.put(LanguageEnum.RU, "Люкc1");
        names.put(LanguageEnum.EN, "Single1");
        room.setName(names);

        Map<LanguageEnum, String> descriptions = new HashMap<>();
        descriptions.put(LanguageEnum.RU, "Самый лучший люкс на свете.1");
        descriptions.put(LanguageEnum.EN, "The best single.1");
        room.setDescription(descriptions);

        RoomDAO roomDAO = new RoomDAOImpl();
        try {
            ExtendedRoom before = roomDAO.getExtendedRoom(roomId);
            roomDAO.editRoom(room);
            ExtendedRoom after = roomDAO.getExtendedRoom(roomId);
            assertTrue(room.equals(after));
            assertFalse(before.equals(after));
            roomDAO.editRoom(before);
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void getAllVisibleRoomTariffs() {
        RoomTariff tariff1 = new RoomTariff();
        tariff1.setId(1);
        tariff1.setCost(0);
        tariff1.setVisibility(true);
        tariff1.setName("Обычный.");
        tariff1.setDescription("Только уборка комнаты.");

        RoomTariff tariff2 = new RoomTariff();
        tariff2.setId(2);
        tariff2.setCost(10);
        tariff2.setVisibility(true);
        tariff2.setName("Продвинктый.");
        tariff2.setDescription("Завтрак и уборка комнаты.");

        RoomTariff tariff3 = new RoomTariff();
        tariff3.setId(3);
        tariff3.setCost(50);
        tariff3.setVisibility(true);
        tariff3.setName("Всё включено.");
        tariff3.setDescription("Завтрак, уборка комнаты, бар.");

        List<RoomTariff> tariffs = new LinkedList<>();
        tariffs.add(tariff1);
        tariffs.add(tariff2);
        tariffs.add(tariff3);
        RoomDAO roomDAO = new RoomDAOImpl();
        try {
            List<RoomTariff> testTariffs = roomDAO.getAllVisibleRoomTariffs(LanguageEnum.RU);
            assertTrue(tariffs.equals(testTariffs));
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void editTariff() {
        ExtendedRoomTariff tariff = new ExtendedRoomTariff();
        tariff.setId(1);
        tariff.setCost(0);
        tariff.setVisibility(true);

        Map<LanguageEnum, String> names = new HashMap<>();
        names.put(LanguageEnum.RU, "Обычный.тест");
        names.put(LanguageEnum.EN, "Common.test");
        tariff.setNames(names);

        Map<LanguageEnum, String> descriptions = new HashMap<>();
        descriptions.put(LanguageEnum.RU, "Только уборка комнаты.тест");
        descriptions.put(LanguageEnum.EN, "Cleaning only.test");
        tariff.setDescriptions(descriptions);

        RoomDAO roomDAO = new RoomDAOImpl();

        try {
            ExtendedRoomTariff before = roomDAO.getExtendedRoomTariff(tariff.getId());
            roomDAO.editTariff(tariff);
            ExtendedRoomTariff after = roomDAO.getExtendedRoomTariff(tariff.getId());
            assertTrue(tariff.equals(after));
            assertFalse(before.equals(after));
            roomDAO.editTariff(before);
        } catch (DAOException e) {
            fail();
        }
    }

    @Test
    public void getExtendedRoomTariff() {
        ExtendedRoomTariff tariff = new ExtendedRoomTariff();
        tariff.setId(1);
        tariff.setCost(0);
        tariff.setVisibility(true);

        Map<LanguageEnum, String> names = new HashMap<>();
        names.put(LanguageEnum.RU, "Обычный.");
        names.put(LanguageEnum.EN, "Common.");
        tariff.setNames(names);

        Map<LanguageEnum, String> descriptions = new HashMap<>();
        descriptions.put(LanguageEnum.RU, "Только уборка комнаты.");
        descriptions.put(LanguageEnum.EN, "Cleaning only.");
        tariff.setDescriptions(descriptions);

        RoomDAO roomDAO = new RoomDAOImpl();

        try {
            ExtendedRoomTariff testTariff = roomDAO.getExtendedRoomTariff(tariff.getId());
            assertTrue(tariff.equals(testTariff));
        } catch (DAOException e) {
            fail();
        }
    }
}