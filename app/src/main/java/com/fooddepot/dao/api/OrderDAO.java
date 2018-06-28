package com.fooddepot.dao.api;

import com.fooddepot.exception.ItemException;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.ui.api.UIOrderService;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.Item;
import com.fooddepot.vo.Order;

public interface OrderDAO {
    void add(Order order) throws ItemException;
    void readByCook(String cookId, final UIOrderService uiOrderService) throws ItemException;
    void readByUser(String userID, final UIOrderService UIOrderService) throws ItemException;
    void readByOrderID(String orderID, final UIOrderService uiOrderService) throws ItemException;
    void delete(String id) throws Exception;
    void update (Order order) throws ItemException;
    void updateRating(Order order, Item item) throws ItemException;
}
