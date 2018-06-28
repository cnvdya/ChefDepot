package com.fooddepot.service.impl;

import com.fooddepot.dao.api.ItemDAO;
import com.fooddepot.dao.api.OrderDAO;
import com.fooddepot.dao.impl.ItemDAOImpl;
import com.fooddepot.dao.impl.OrderDAOImpl;
import com.fooddepot.exception.ItemException;
import com.fooddepot.service.api.OrderService;
import com.fooddepot.ui.api.UIOrderService;
import com.fooddepot.vo.Item;
import com.fooddepot.vo.Order;

public class OrderServiceImpl implements OrderService {

    OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public void add(Order order) {
        try {
            orderDAO.add(order);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            orderDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readByCook(String cookId, UIOrderService uiOrderService) {
        try {
            orderDAO.readByCook(cookId,uiOrderService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readByUser(String userId, UIOrderService uiOrderService) {
        try {
            orderDAO.readByUser(userId,uiOrderService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readByOrderID(String orderId, UIOrderService uiOrderService) {
        try {
            orderDAO.readByOrderID(orderId,uiOrderService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void update(Order order) {
        try{
            orderDAO.update(order);
        }catch (ItemException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateRating(Order order, Item item) {
        try {
            orderDAO.updateRating(order,item);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

}
