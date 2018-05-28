package com.epam.final_task.controller.command;

import com.epam.final_task.entity.UserRoleEnum;

import java.util.*;

/**
 * Class user access supervisor.
 *
 * @author Ihnat Mikhalkovich
 * @see UserRoleEnum
 * @see CommandTypeEnum
 * @since 1.0
 */
public class AccessDirector {

    private final Map<UserRoleEnum, Set<CommandTypeEnum>> accessMap = new EnumMap<>(UserRoleEnum.class);

    /**
     * Initialization. Arrangement of privileges.
     */
    public AccessDirector() {
        Set<CommandTypeEnum> guestAccessSet = new HashSet<>();
        guestAccessSet.add(CommandTypeEnum.ROOM);
        guestAccessSet.add(CommandTypeEnum.RESERVATION);
        guestAccessSet.add(CommandTypeEnum.LOGIN);
        guestAccessSet.add(CommandTypeEnum.OFFERS);
        guestAccessSet.add(CommandTypeEnum.REGISTRATION);
        guestAccessSet.add(CommandTypeEnum.LANGUAGE);
        guestAccessSet.add(CommandTypeEnum.ERROR);
        accessMap.put(UserRoleEnum.GUEST, guestAccessSet);

        Set<CommandTypeEnum> bannedAccessSet = new HashSet<>();
        bannedAccessSet.add(CommandTypeEnum.ROOM);
        bannedAccessSet.add(CommandTypeEnum.RESERVATION);
        bannedAccessSet.add(CommandTypeEnum.OFFERS);
        bannedAccessSet.add(CommandTypeEnum.LANGUAGE);
        bannedAccessSet.add(CommandTypeEnum.ERROR);
        bannedAccessSet.add(CommandTypeEnum.PROFILE);
        bannedAccessSet.add(CommandTypeEnum.HISTORY);
        bannedAccessSet.add(CommandTypeEnum.CHANGE_PASSWORD_SAVE);
        bannedAccessSet.add(CommandTypeEnum.EDIT_PROFILE_SAVE);
        bannedAccessSet.add(CommandTypeEnum.EDIT_PROFILE_FORM);
        bannedAccessSet.add(CommandTypeEnum.LOGOUT);
        bannedAccessSet.add(CommandTypeEnum.RETURN_THE_ROOM);
        bannedAccessSet.add(CommandTypeEnum.REPLENISH_BALANCE);
        accessMap.put(UserRoleEnum.BANNED, bannedAccessSet);

        Set<CommandTypeEnum> userAccessSet = new HashSet<>(bannedAccessSet);
        userAccessSet.add(CommandTypeEnum.TAKE_ROOM);
        userAccessSet.add(CommandTypeEnum.PAY_ROOM);
        accessMap.put(UserRoleEnum.USER, userAccessSet);

        Set<CommandTypeEnum> moderatorAccessSet = new HashSet<>(userAccessSet);
        moderatorAccessSet.add(CommandTypeEnum.ADD_ROOM_SAVE);
        moderatorAccessSet.add(CommandTypeEnum.ADD_ROOM_FORM);
        moderatorAccessSet.add(CommandTypeEnum.EDIT_ROOM_FORM);
        moderatorAccessSet.add(CommandTypeEnum.EDIT_ROOM_SAVE);
        moderatorAccessSet.add(CommandTypeEnum.TARIFFS);
        moderatorAccessSet.add(CommandTypeEnum.ADD_TARIFF_FORM);
        moderatorAccessSet.add(CommandTypeEnum.ADD_TARIFF_SAVE);
        moderatorAccessSet.add(CommandTypeEnum.EDIT_TARIFF_FORM);
        moderatorAccessSet.add(CommandTypeEnum.EDIT_TARIFF_SAVE);
        accessMap.put(UserRoleEnum.MODERATOR, moderatorAccessSet);

        Set<CommandTypeEnum> administratorAccessSet = new HashSet<>(Arrays.asList(CommandTypeEnum.values()));
        accessMap.put(UserRoleEnum.ADMINISTRATOR, administratorAccessSet);
    }

    /**
     * User role verification for privilege.
     *
     * @param role user role
     * @param command privilege
     * @return true if the user has access
     *         false if the user has not access
     */
    public boolean checkAccess(UserRoleEnum role, CommandTypeEnum command) {
        Set<CommandTypeEnum> access = accessMap.get(role);
        return access.contains(command);
    }

}
