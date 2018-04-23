package com.epam.final_task.service;

import com.epam.final_task.entity.User;
import com.epam.final_task.entity.UserRoleEnum;

import java.util.List;

public interface UserService {

    boolean isUserInit(String email);

    void addUser(User user, String password);

    User getUser(String email);

    List<User> getAllUsers();

    boolean checkUser(String email, String password);

    boolean changeUserPassword(String email, String oldPassword, String newPassword);

    default UserRoleEnum getRole(String email) {
        User user = getUser(email);
        return user.getRole();
    }

}
