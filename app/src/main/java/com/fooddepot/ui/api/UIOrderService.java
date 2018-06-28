package com.fooddepot.ui.api;

import com.fooddepot.vo.Order;

import java.util.List;

public interface UIOrderService {
    void displayAllOrders(List<Order> orders);
    void displayOrder(Order order);
}
