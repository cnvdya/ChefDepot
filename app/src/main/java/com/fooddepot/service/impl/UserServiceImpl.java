package com.fooddepot.service.impl;

import com.fooddepot.dao.api.ItemDAO;
import com.fooddepot.dao.api.UserDAO;
import com.fooddepot.dao.impl.ItemDAOImpl;
import com.fooddepot.dao.impl.UserDAOImpl;
import com.fooddepot.exception.ItemException;
import com.fooddepot.service.api.UserService;
import com.fooddepot.ui.api.UIUserService;
import com.fooddepot.vo.User;

public class UserServiceImpl implements UserService {

    UserDAO userDAO = new UserDAOImpl();


    @Override
    public void add(User user) {
        try {
            userDAO.add(user);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void read(String userId, UIUserService uiUserService) {
        try {
            userDAO.read(userId,uiUserService);
        } catch (ItemException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readForCook(String userId, UIUserService uiUserService) {

    }

    @Override
    public void readAll(UIUserService uiItemService) {

    }

    @Override
    public void update(User user) {
        try {
            userDAO.update(user);
        } catch (ItemException e) {
            e.printStackTrace();
        }

    }
}
