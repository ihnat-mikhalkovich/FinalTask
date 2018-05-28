package com.epam.final_task.controller.command;

import java.util.HashMap;
import java.util.Map;

public class CommandTypeMap {

    private final static CommandTypeMap instance = new CommandTypeMap();

    private final Map<String, CommandTypeEnum> stringCommands = new HashMap<>();

    private CommandTypeMap() {
        stringCommands.put("reservation", CommandTypeEnum.RESERVATION);
        stringCommands.put("logout", CommandTypeEnum.LOGOUT);
        stringCommands.put("login", CommandTypeEnum.LOGIN);
        stringCommands.put("error", CommandTypeEnum.ERROR);
        stringCommands.put("registration", CommandTypeEnum.REGISTRATION);
        stringCommands.put("language", CommandTypeEnum.LANGUAGE);
        stringCommands.put("users", CommandTypeEnum.USERS);
        stringCommands.put("offers", CommandTypeEnum.OFFERS);
        stringCommands.put("add_room_save", CommandTypeEnum.ADD_ROOM_SAVE);
        stringCommands.put("edit_room_form", CommandTypeEnum.EDIT_ROOM_FORM);
        stringCommands.put("edit_room_save", CommandTypeEnum.EDIT_ROOM_SAVE);
        stringCommands.put("take_room", CommandTypeEnum.TAKE_ROOM);
        stringCommands.put("profile", CommandTypeEnum.PROFILE);
        stringCommands.put("return_the_room", CommandTypeEnum.RETURN_THE_ROOM);
        stringCommands.put("edit_profile_form", CommandTypeEnum.EDIT_PROFILE_FORM);
        stringCommands.put("edit_profile_save", CommandTypeEnum.EDIT_PROFILE_SAVE);
        stringCommands.put("history", CommandTypeEnum.HISTORY);
        stringCommands.put("change_password_save", CommandTypeEnum.CHANGE_PASSWORD_SAVE);
        stringCommands.put("edit_user", CommandTypeEnum.EDIT_USER);
        stringCommands.put("edit_password", CommandTypeEnum.EDIT_PASSWORD);
        stringCommands.put("add_room_form", CommandTypeEnum.ADD_ROOM_FORM);
        stringCommands.put("pay_room", CommandTypeEnum.PAY_ROOM);
        stringCommands.put("orders", CommandTypeEnum.ORDERS);
        stringCommands.put("allow_order", CommandTypeEnum.ALLOW_ORDER);
        stringCommands.put("replenish_balance", CommandTypeEnum.REPLENISH_BALANCE);
        stringCommands.put("tariffs", CommandTypeEnum.TARIFFS);
        stringCommands.put("add_tariff_form", CommandTypeEnum.ADD_TARIFF_FORM);
        stringCommands.put("add_tariff_save", CommandTypeEnum.ADD_TARIFF_SAVE);
        stringCommands.put("edit_tariff_form", CommandTypeEnum.EDIT_TARIFF_FORM);
        stringCommands.put("edit_tariff_save", CommandTypeEnum.EDIT_TARIFF_SAVE);
        stringCommands.put("room", CommandTypeEnum.ROOM);
        stringCommands.put("userHistory", CommandTypeEnum.USER_HISTORY);
    }

    public static CommandTypeMap getInstance() {
        return instance;
    }

    public CommandTypeEnum getCommand(String stringCommand) {
        return stringCommands.get(stringCommand);
    }

}
