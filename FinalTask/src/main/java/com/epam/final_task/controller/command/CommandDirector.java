package com.epam.final_task.controller.command;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class contain the {@see Map} of all command implementations.
 *
 * @author Ihnat Mikhalkovich
 * @see Command
 * @see CommandTypeEnum
 * @since 1.0
 */
public class CommandDirector {

    private Map<CommandTypeEnum, Command> commands = new EnumMap<>(CommandTypeEnum.class);

    /**
     * Initialization.
     */
    public CommandDirector() {
        commands.put(CommandTypeEnum.REGISTRATION, new RegistrationCommand());
        commands.put(CommandTypeEnum.RESERVATION, new ReservationCommand());
        commands.put(CommandTypeEnum.LOGIN, new LoginCommand());
        commands.put(CommandTypeEnum.LANGUAGE, new LanguageCommand());
        commands.put(CommandTypeEnum.USERS, new UsersCommand());
        commands.put(CommandTypeEnum.LOGOUT, new LogoutCommand());
        commands.put(CommandTypeEnum.OFFERS, new OffersCommand());
        commands.put(CommandTypeEnum.ERROR, new ErrorCommand());
        commands.put(CommandTypeEnum.EDIT_ROOM_FORM, new EditRoomFormCommand());
        commands.put(CommandTypeEnum.EDIT_ROOM_SAVE, new EditRoomSaveCommand());
        commands.put(CommandTypeEnum.ADD_ROOM_SAVE, new AddRoomSaveCommand());
        commands.put(CommandTypeEnum.TAKE_ROOM, new TakeRoomCommand());
        commands.put(CommandTypeEnum.RETURN_THE_ROOM, new ReturnTheRoomCommand());
        commands.put(CommandTypeEnum.PROFILE, new ProfileCommand());
        commands.put(CommandTypeEnum.EDIT_PROFILE_FORM, new EditProfileFormCommand());
        commands.put(CommandTypeEnum.EDIT_PROFILE_SAVE, new EditProfileSaveCommand());
        commands.put(CommandTypeEnum.HISTORY, new HistoryCommand());
        commands.put(CommandTypeEnum.CHANGE_PASSWORD_SAVE, new ChangePasswordSaveCommand());
        commands.put(CommandTypeEnum.EDIT_USER, new EditUserCommand());
        commands.put(CommandTypeEnum.EDIT_PASSWORD, new EditPasswordCommand());
        commands.put(CommandTypeEnum.ADD_ROOM_FORM, new AddRoomFormCommand());
        commands.put(CommandTypeEnum.PAY_ROOM, new PayRoomCommand());
        commands.put(CommandTypeEnum.ORDERS, new OrdersCommand());
        commands.put(CommandTypeEnum.ALLOW_ORDER, new AllowOrderCommand());
        commands.put(CommandTypeEnum.REPLENISH_BALANCE, new ReplenishBalanceCommand());
        commands.put(CommandTypeEnum.TARIFFS, new TariffsCommand());
        commands.put(CommandTypeEnum.ADD_TARIFF_FORM, new AddTariffFormCommand());
        commands.put(CommandTypeEnum.ADD_TARIFF_SAVE, new AddTariffSaveCommand());
        commands.put(CommandTypeEnum.EDIT_TARIFF_FORM, new EditTariffFormCommand());
        commands.put(CommandTypeEnum.EDIT_TARIFF_SAVE, new EditTariffSaveCommand());
        commands.put(CommandTypeEnum.ROOM, new RoomCommand());
        commands.put(CommandTypeEnum.USER_HISTORY, new UserHistoryCommand());
    }

    /**
     * Used for get implementation of {@code Command} by {@code CommandTypeEnum}.
     *
     * @param type type of needed implementation of {@code Command}
     * @return implementation of {@code Command}
     * @see Command
     * @see CommandTypeEnum
     */
    public Command getCommand(CommandTypeEnum type) {
        return commands.get(type);
    }

}
