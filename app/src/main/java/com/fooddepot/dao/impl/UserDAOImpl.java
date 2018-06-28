package com.fooddepot.dao.impl;

import android.util.Log;

import com.fooddepot.dao.api.UserDAO;
import com.fooddepot.exception.ItemException;
import com.fooddepot.ui.api.UIItemService;
import com.fooddepot.ui.api.UIUserService;
import com.fooddepot.util.DAOUtil;
import com.fooddepot.util.PathUtil;
import com.fooddepot.vo.Item;
import com.fooddepot.vo.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mudrita on 5/3/18.
 */

public class UserDAOImpl implements UserDAO {

    String TAG = "UserDAOImpl";

    String userPath =  PathUtil.getUserPath();


    @Override
    public void add(User user) throws ItemException {

        try{
//            String userID = DAOUtil.getDatabaseReference().push().getKey();
//            user.setUid(userID);
            DAOUtil.getDatabaseReference().child(userPath).child(user.getUid()).setValue(user);
            //DAOUtil.getDatabaseReference().child(itemPath).child(DAOUtil.getDatabaseReference().push().getKey()).setValue(item);
        }catch(Exception exception){
            Log.e(TAG,"Error adding user",exception);
            throw new ItemException("Error",exception);
        }
    }

    @Override
    public void read(String userID, final UIUserService uiUserService) throws ItemException {

        try{
            System.out.println("User details..");

            DAOUtil.getDatabaseReference().child(PathUtil.getUserIdPath(userID)).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<User> genericTypeIndicator = new GenericTypeIndicator<User>() {
                            };
                            User userDetail = dataSnapshot.getValue(genericTypeIndicator);
                            System.out.println("Lis of all item in daoimpl.."+userDetail.getName());
                            uiUserService.displayUser(userDetail);


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
    public void delete(String userID) throws Exception {
        String userIDPath =  PathUtil.getUserIdPath(userID);
        DAOUtil.getDatabaseReference().child(userIDPath).removeValue();

    }

    @Override
    public void update(User user) throws ItemException {
        try{
            DAOUtil.getDatabaseReference().child(PathUtil.getUserIdPath(user.getUid())).setValue(user);
            //DAOUtil.getDatabaseReference().child(itemPath).child(DAOUtil.getDatabaseReference().push().getKey()).setValue(item);
        }catch(Exception exception){
            Log.e(TAG,"Error updating user",exception);
            throw new ItemException("Error",exception);
        }
    }
}
