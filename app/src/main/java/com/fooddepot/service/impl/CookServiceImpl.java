package com.fooddepot.service.impl;

import com.fooddepot.dao.api.CookDAO;
import com.fooddepot.dao.api.ItemDAO;
import com.fooddepot.dao.impl.CookDAOImpl;
import com.fooddepot.dao.impl.ItemDAOImpl;
import com.fooddepot.exception.ItemException;
import com.fooddepot.service.api.CookService;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.vo.Cook;

/**
 * Created by mudrita on 5/3/18.
 */

public class CookServiceImpl implements CookService {

    CookDAO cookDAO = new CookDAOImpl();

    @Override
    public void add(Cook cook) {
        try {
            cookDAO.add(cook);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            cookDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void read(String cookId, UICookService uiCookService) {
        try {
            cookDAO.read(cookId,uiCookService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Cook cook) {
        try {
            cookDAO.update(cook);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }
}
