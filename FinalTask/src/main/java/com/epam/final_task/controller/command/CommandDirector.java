package com.epam.final_task.controller.command;

import java.util.EnumMap;
import java.util.Map;

public class CommandDirector {

    private Map<CommandTypeEnum, Command> commands = new EnumMap<>(CommandTypeEnum.class);

    public CommandDirector() {
        commands.put(CommandTypeEnum.REGISTRATION, new RegistrationCommand());
        commands.put(CommandTypeEnum.RESERVATION, new ReservationCommand());
        commands.put(CommandTypeEnum.LOGIN, new LoginCommand());
        commands.put(CommandTypeEnum.LANGUAGE, new LanguageCommand());
        commands.put(CommandTypeEnum.USERS, new UsersCommand());
        commands.put(CommandTypeEnum.LOGOUT, new LoginCommand());
        commands.put(CommandTypeEnum.OFFERS, new OffersCommand());
    }

    public Command getCommand(CommandTypeEnum type) {
        return commands.get(type);
    }

}
