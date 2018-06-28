package com.fooddepot.dao.impl;

import android.util.Log;

import com.fooddepot.dao.api.CookDAO;
import com.fooddepot.exception.ItemException;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.util.DAOUtil;
import com.fooddepot.util.PathUtil;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by mudrita on 5/3/18.
 */

public class CookDAOImpl implements CookDAO {

    String TAG = "CookDAOImpl";

    String cookPath =  PathUtil.getCookPath();

    @Override
    public void add(Cook cook) throws ItemException {
        try{
//            String cookId = DAOUtil.getDatabaseReference().push().getKey();
//            cook.setUid(cookId);
            DAOUtil.getDatabaseReference().child(cookPath).child(cook.getUid()).setValue(cook);
            //DAOUtil.getDatabaseReference().child(itemPath).child(DAOUtil.getDatabaseReference().push().getKey()).setValue(item);
        }catch(Exception exception){
            Log.e(TAG,"Error adding item",exception);
            throw new ItemException("Error",exception);
        }
    }

    @Override
    public void read(String cookId, final UICookService uiCookService) throws ItemException {
        try{
            System.out.println("cook details..");

            DAOUtil.getDatabaseReference().child(PathUtil.getCookIdPath(cookId)).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<Cook> genericTypeIndicator = new GenericTypeIndicator<Cook>() {
                            };
                            Cook cookInfo = dataSnapshot.getValue(genericTypeIndicator);
                            uiCookService.displayCook(cookInfo);

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
        String cookIDPath =  PathUtil.getCookIdPath(id);
        DAOUtil.getDatabaseReference().child(cookIDPath).removeValue();
    }

    @Override
    public void update(Cook cook) throws ItemException {
        try{
            DAOUtil.getDatabaseReference().child(PathUtil.getCookIdPath(cook.getUid())).setValue(cook);
            //DAOUtil.getDatabaseReference().child(itemPath).child(DAOUtil.getDatabaseReference().push().getKey()).setValue(item);
        }catch(Exception exception){
            Log.e(TAG,"Error adding item",exception);
            throw new ItemException("Error",exception);
        }

    }

}
