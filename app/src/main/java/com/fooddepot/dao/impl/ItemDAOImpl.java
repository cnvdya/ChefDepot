package com.fooddepot.dao.impl;

import android.util.Log;

import com.fooddepot.dao.api.ItemDAO;
import com.fooddepot.exception.ItemException;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.util.DAOUtil;
import com.fooddepot.util.PathUtil;
import com.fooddepot.vo.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ravisha on 4/26/18.
 */

public class ItemDAOImpl implements ItemDAO {

    String TAG = "ItemDAOImpl";

    String itemPath =  PathUtil.getItemPath();

    @Override
    public void add(Item item) throws ItemException{
        try{
//            String itemId = DAOUtil.getDatabaseReference().push().getKey();
//            item.setItemId(itemId);

            DAOUtil.getDatabaseReference().child(itemPath).child(item.getItemId()).setValue(item);

            DAOUtil.getDatabaseReference().child(PathUtil.getCookItemPath(item.getCookId())).child(item.getItemId()).setValue(item);





            //DAOUtil.getDatabaseReference().child(itemPath).child(DAOUtil.getDatabaseReference().push().getKey()).setValue(item);
        }catch(Exception exception){
            Log.e(TAG,"Error adding item",exception);
            throw new ItemException("Error",exception);
        }
    }

    @Override
    public void read(String itemId, final UIItemService uiItemService) throws ItemException {

        try{
            System.out.println("Lis of all items..");

            DAOUtil.getDatabaseReference().child(PathUtil.getItemIdPath(itemId)).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<Item> genericTypeIndicator = new GenericTypeIndicator<Item>() {
                            };
                            Item attendanceInfo = dataSnapshot.getValue(genericTypeIndicator);
                            System.out.println("Lis of all item in daoimpl.."+attendanceInfo.getCategory());
                            uiItemService.displayItem(attendanceInfo);


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
        String itemIDPath =  PathUtil.getItemIdPath(id);
        DAOUtil.getDatabaseReference().child(itemIDPath).removeValue();
    }

    @Override
    public void update(String id, Item item) throws ItemException{
        try{
            DAOUtil.getDatabaseReference().child(PathUtil.getItemIdPath(id)).setValue(item);
            DAOUtil.getDatabaseReference().child(PathUtil.getCookItemIdPath(item.getCookId(),item.getItemId())).setValue(item);
        }catch(Exception exception){
            Log.e(TAG,"Error adding item",exception);
            throw new ItemException("Error",exception);
        }
    }


    @Override
    public void readAllforCook(String cookId, final UIItemService uiItemService) throws ItemException {

        try{
            System.out.println("List of all items for given cook..");

            DAOUtil.getDatabaseReference().child(PathUtil.getCookItemPath(cookId)).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<Map<String,Item>> genericTypeIndicator = new GenericTypeIndicator<Map<String,Item>>() {
                            };
                            Map<String,Item> itemMap = dataSnapshot.getValue(genericTypeIndicator);
                            List<Item> itemList = new ArrayList<>();
                            if(itemMap!=null) {
                                for (String key : itemMap.keySet()) {
                                    Item item = itemMap.get(key);
                                    itemList.add(item);
                                }
                            }

                            uiItemService.displayAllItems(itemList);
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
    public void readAll(final UIItemService uiItemService) throws ItemException {
        try{
            System.out.println("List of all items..");
            DAOUtil.getDatabaseReference().child(PathUtil.getItemPath()).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("List of all item in daoimpl..inside onDataChange");
                            GenericTypeIndicator<Map<String,Item>> genericTypeIndicator = new GenericTypeIndicator<Map<String,Item>>() {
                            };
                            Map<String,Item> itemMap = dataSnapshot.getValue(genericTypeIndicator);
                            List<Item> itemList = new ArrayList<>();
                            for(String key:itemMap.keySet()){
                                Item item = itemMap.get(key);

                                System.out.println("List of all item in daoimpl.."+item.getName());

                                itemList.add(item);
                            }

                            uiItemService.displayAllItems(itemList);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }
            catch(Throwable exception)
            {
            Log.e(TAG,"Error getting item",exception);
            throw new ItemException("Error",new Exception());
        }

    }


    @Override
    public void searchItems(String searchKey, String searchValue, final UIItemService uiItemService) throws ItemException {

        try {
            Log.d(TAG,"search key: "+searchKey);
            Log.d(TAG,"search value: "+searchValue);

            DatabaseReference myRef = DAOUtil.getDatabaseReference();
            myRef.child(itemPath).orderByChild(searchKey)
                    .startAt(searchValue).endAt(searchValue+"\uf8ff")
                    //.equalTo(searchValue)
                    .addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<Map<String, Item>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Item>>() {
                            };
                            Map<String, Item> itemsMap = dataSnapshot.getValue(genericTypeIndicator);
                            if(itemsMap!=null && itemsMap.size()>0) {
                                List<Item> itemList = new ArrayList<>();
                                for (String key : itemsMap.keySet()) {
                                    Item item = itemsMap.get(key);

                                    //System.out.println("Lis of all item in daoimpl.."+item.getName());

                                    itemList.add(item);
                                }
                                Log.d(TAG,"returning data from here:"+itemList.size());
                                uiItemService.displayAllItems(itemList);
                                //uiItemService.displayItemsList(itemsMap);
                            }
                            else
                                Log.d(TAG,"returning data from here:"+0);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        } catch (Exception exception) {
            throw new ItemException("Error", exception);

        }
    }
}
