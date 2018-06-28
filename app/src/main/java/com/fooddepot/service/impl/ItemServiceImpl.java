package com.fooddepot.service.impl;

import com.fooddepot.dao.api.ItemDAO;
import com.fooddepot.dao.impl.ItemDAOImpl;
import com.fooddepot.exception.ItemException;
import com.fooddepot.service.api.ItemService;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.vo.Item;

/**
 * Created by ravisha on 4/26/18.
 */

public class ItemServiceImpl implements ItemService {

    ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public void add(Item item) {
        try {
            itemDAO.add(item);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            itemDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read(String itemId, UIItemService uiItemService) {
        try {
            itemDAO.read(itemId,uiItemService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readAllforCook(String cookId, UIItemService uiItemService) {
        try {
            itemDAO.readAllforCook(cookId,uiItemService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readAll(UIItemService uiItemService) {
        try {
            itemDAO.readAll(uiItemService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
        catch (Exception e) {

            System.out.println("Bada wala exceptio ");
            System.out.println("Bada wala exceptio "+e.getCause());
            e.printStackTrace();
        }
    }

    @Override
    public void update(String itemId, Item item) {
        try {
            itemDAO.update(itemId,item);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchItems(String searchKey,String searchValue,UIItemService uiItemService){
        try {
            itemDAO.searchItems(searchKey,searchValue,uiItemService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }
}
