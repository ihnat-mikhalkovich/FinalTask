package com.epam.final_task.service.validation;

import com.epam.final_task.entity.ExtendedRoom;
import com.epam.final_task.entity.ExtendedRoomTariff;
import com.epam.final_task.entity.ReservedRoom;

import java.util.Date;

/**
 * Class provides methods to verify the arguments RoomService methods
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class RoomServiceValidator {

    /**
     * Validate input dates.
     *
     * @param arrival check in date
     * @param departure check out date
     * @return true if the dates are satisfactory
     *         false if dates are not satisfactory
     * @see Date
     */
    public static boolean dateRangeValidation(Date arrival, Date departure) {
        Date currentDate = new Date();
        return (arrival != null) && (departure != null)
                && (currentDate.before(arrival))
                && (currentDate.before(departure))
                && (arrival.before(departure));
    }

    /**
     * Validate input id.
     *
     * @param roomId id of room.
     * @return true if the id is satisfactory
     *         false if the id is not satisfactory
     */
    public static boolean idValidation(int roomId) {
        return (roomId > 0);
    }

    /**
     * Validate object of {@code ExtendedRoom}
     *
     * @param room inspected object
     * @return true if the {@code room} is satisfactory
     *         false if the {@code room} is not satisfactory
     * @see ExtendedRoom
     */
    public static boolean extendedRoomValidation(ExtendedRoom room) {
        if (room == null) {
            return false;
        }
        boolean validateCost = room.getCost() > 0;
        boolean validateNumberOfPersons = room.getNumberOfPersons() > 0;
        return validateCost && validateNumberOfPersons;
    }

    /**
     * Validate object of {@code ReservedRoom}
     *
     * @param reservedRoom inspected object
     * @return true if the {@code reservedRoom} is satisfactory
     *         false if the {@code reservedRoom} is not satisfactory
     * @see ReservedRoom
     */
    public static boolean reservedRoomValidation(ReservedRoom reservedRoom) {
        if (reservedRoom == null) {
            return false;
        }
        Date arrival = reservedRoom.getRange().getArrival();
        Date departure = reservedRoom.getRange().getDeparture();
        Date currentDate = new Date();
        return reservedRoom.getUserId() > 0
                && reservedRoom.getRoomId() > 0
                && reservedRoom.getRoomsAmount() > 0
                && (arrival != null) && (departure != null)
                && (currentDate.before(arrival))
                && (currentDate.before(departure))
                && (arrival.before(departure))
                && reservedRoom.getTariffId() > 0;
    }

    /**
     * Validate input id.
     *
     * @param reservedRoomId id of {@code ReservedRoom} object.
     * @return true if the id is satisfactory
     *         false if the id is not satisfactory
     * @see ReservedRoom
     */
    public static boolean reservedRoomIdValidation(int reservedRoomId) {
        return (reservedRoomId > 0);
    }

    /**
     * Validate page number.
     *
     * @param pageNumber number of page.
     * @return true if the {@code pageNumber} is satisfactory
     *         false if the {@code pageNumber} is not satisfactory
     */
    public static boolean pageNumberValidation(int pageNumber) {
        return pageNumber > 0;
    }

    /**
     * Validate object of {@code ExtendedRoomTariff}
     *
     * @param tariff inspected object
     * @return true if the {@code tariff} is satisfactory
     *         false if the {@code tariff} is not satisfactory
     * @see ExtendedRoomTariff
     */
    public static boolean extendedTariffValidation(ExtendedRoomTariff tariff) {
        return tariff != null
                && tariff.getCost() > 0
                && tariff.getNames() != null
                && tariff.getDescriptions() != null;
    }

}
