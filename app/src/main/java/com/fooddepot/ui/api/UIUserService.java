package com.fooddepot.ui.api;

import com.fooddepot.vo.User;

import java.util.List;

public interface UIUserService {
    void displayAllUsers(List<User> user);
    void displayUser(User user);
}
