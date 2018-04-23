package com.epam.final_task.service.impl;

import com.epam.final_task.entity.User;
import com.epam.final_task.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public boolean isUserInit(String email) {
        return false;
    }

    @Override
    public User get(String email) {
        return null;
    }

    @Override
    public boolean remove(String email) {
        return false;
    }

    @Override
    public boolean put(User user) {
        return false;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
