package com.fooddepot.util;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;


/**
 * Created by ravisha on 10/9/16.
 */
public class DAOUtil {
    private static DatabaseReference databaseReference;

    static{

        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public static DatabaseReference getDatabaseReference() {
        return databaseReference;
    }



}
