package com.fooddepot.dao.impl;

import android.util.Log;

import com.fooddepot.dao.api.OrderDAO;
import com.fooddepot.exception.ItemException;
import com.fooddepot.ui.api.UIOrderService;
import com.fooddepot.util.DAOUtil;
import com.fooddepot.util.PathUtil;
import com.fooddepot.vo.Item;
import com.fooddepot.vo.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAOImpl implements OrderDAO {

    String TAG = "OrderDAOImpl";

    String orderPath =  PathUtil.getOrderPath();


    @Override
    public void add(Order order) throws ItemException {
        try{


            String orderId = order.getOrderId();
            //order.setOrderId(orderId);

            DAOUtil.getDatabaseReference().child(orderPath).child(orderId).setValue(order);

            DAOUtil.getDatabaseReference().child(PathUtil.getCookOrderPath(order.getItem().getCookId())).child(orderId).setValue(order);
            DAOUtil.getDatabaseReference().child(PathUtil.getUserOrderPath(order.getUserId())).child(orderId).setValue(order);

            //DAOUtil.getDatabaseReference().child(itemPath).child(DAOUtil.getDatabaseReference().push().getKey()).setValue(item);
        }catch(Exception exception){
            Log.e(TAG,"Error adding order",exception);
            throw new ItemException("Error",exception);
        }

    }

    @Override
    public void readByCook(String cookId, final UIOrderService uiOrderService) throws ItemException {
        try{
            System.out.println("List of all orders for given cook..");

            DAOUtil.getDatabaseReference().child(PathUtil.getCookOrderPath(cookId)).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<Map<String, Order>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Order>>() {
                            };
                            Map<String, Order> orderMap = dataSnapshot.getValue(genericTypeIndicator);
                            List<Order> orderList = new ArrayList<>();
                            if (orderMap != null) {
                                for (String key : orderMap.keySet()) {
                                    Order order = orderMap.get(key);

                                    //System.out.println("Lis of all item in daoimpl.."+order.getUserName());
                                    orderList.add(order);
                                }
                                uiOrderService.displayAllOrders(orderList);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }catch(Exception exception){
            Log.e(TAG,"Error getting item",exception);
            throw new ItemException("Error",exception);

        }
    }

    @Override
    public void readByUser(String userID, final UIOrderService uiOrderService) throws ItemException {

        try{
            System.out.println("List of all orders for given user..");

            DAOUtil.getDatabaseReference().child(PathUtil.getUserOrderPath(userID)).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<Map<String, Order>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Order>>() {
                            };
                            Map<String, Order> orderMap = dataSnapshot.getValue(genericTypeIndicator);
                            List<Order> orderList = new ArrayList<>();
                            if (orderMap != null) {
                                for (String key : orderMap.keySet()) {
                                    Order order = orderMap.get(key);

                                    //System.out.println("Lis of all item in daoimpl.."+order.getUserName());
                                    orderList.add(order);
                                }
                                uiOrderService.displayAllOrders(orderList);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }catch(Exception exception){
            Log.e(TAG,"Error getting item",exception);
            throw new ItemException("Error",exception);

        }

    }

    @Override
    public void readByOrderID(String orderID, final UIOrderService uiOrderService) throws ItemException {
        try{
            System.out.println("Order details..");

            DAOUtil.getDatabaseReference().child(PathUtil.getOrderIdPath(orderID)).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<Order> genericTypeIndicator = new GenericTypeIndicator<Order>() {
                            };
                            Order order = dataSnapshot.getValue(genericTypeIndicator);
                            System.out.println("order details.."+order.getItem().getCookId());
                            uiOrderService.displayOrder(order);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }catch(Exception exception){
            Log.e(TAG,"Error getting item",exception);
            throw new ItemException("Error",exception);

        }
    }

    @Override
    public void delete(String id) throws Exception {

    }

    @Override
    public void update(Order order) throws ItemException {
        try{
            DAOUtil.getDatabaseReference().child(PathUtil.getOrderIdPath(order.getOrderId())).setValue(order);
            DAOUtil.getDatabaseReference().child(PathUtil.getCookOrderIdPath(order.getItem().getCookId(),order.getOrderId())).setValue(order);
            DAOUtil.getDatabaseReference().child(PathUtil.getUserOrderIdPath(order.getUserId(),order.getOrderId())).setValue(order);
        }catch(Exception exception){
            Log.e(TAG,"Error updating order",exception);
            throw new ItemException("Error",exception);
        }
    }

    @Override
    public void updateRating(Order order, Item item) throws ItemException {
        try{
            DAOUtil.getDatabaseReference().child(PathUtil.getOrderIdPath(order.getOrderId())).setValue(order);
            DAOUtil.getDatabaseReference().child(PathUtil.getUserOrderIdPath(order.getUserId(),order.getOrderId())).setValue(order);

            DAOUtil.getDatabaseReference().child(PathUtil.getCookItemIdPath(item.getCookId(),item.getItemId())).setValue(item);
            DAOUtil.getDatabaseReference().child(PathUtil.getItemIdPath(item.getItemId())).setValue(item);
        }catch(Exception exception){
            Log.e(TAG,"Error updating order",exception);
            throw new ItemException("Error",exception);
        }
    }





}
