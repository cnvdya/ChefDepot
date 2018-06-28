package com.fooddepot.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fooddepot.R;
import com.fooddepot.activity.order.CalculateDistance;
import com.fooddepot.service.api.CookService;
import com.fooddepot.service.api.UserService;
import com.fooddepot.service.impl.CookServiceImpl;
import com.fooddepot.service.impl.UserServiceImpl;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.ui.api.UIUserService;
import com.fooddepot.vo.Cook;
import com.fooddepot.vo.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class RoleActivity extends AppCompatActivity implements UIUserService,UICookService{

    private FirebaseAuth mAuth;
    private UserService userService;
    private CookService cookService;
    private String TAG="RoleActivity";
    private FirebaseUser currentUser;
    private User user;

//    Bundle bundle;
//    double currentLatitude,currentLogitude;
//    CalculateDistance calculateDistance;
//    private LocationManager locationManager;
//    private LocationListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        mAuth= FirebaseAuth.getInstance();



//        calculateDistance = new CalculateDistance();
//
//        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
//
//        listener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                currentLatitude=location.getLongitude();
//                currentLogitude=location.getLatitude();
//                Log.d("SearchFragment","currentLatitude"+currentLatitude);
//                Log.d("SearchFragment","currentLogitude"+currentLogitude);
//            }
//
//            @Override
//            public void onStatusChanged(String s, int i, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String s) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String s) {
//
//                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(i);
//            }
//        };
//
////        String dist=calculateDistance.getDistance(currentLatitude,currentLogitude,destLatitude,destLongitude);
////        Log.d("SearchFragment","distance found"+dist);
//
//
//
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
//                        ,10);
//            }
//            return;
//        }
//        else{
//
//            locationManager.requestLocationUpdates("gps", 60000, 0, listener);
//        }




//
    }

    public void onClickCook(View view){

        Log.d(TAG,"cook button clicked");
        userService=new UserServiceImpl();
        userService.read(currentUser.getUid(),RoleActivity.this);

    }

    public void onClickOrder(View view){
        Intent i = new Intent(this,OrderActivity.class);

        startActivity(i);
    }

    @Override
    public void onStart(){
        super.onStart();
        currentUser = mAuth.getCurrentUser();
       if (currentUser == null) {
            Intent i = new Intent(RoleActivity.this,RegisterActivity.class);
            startActivity(i);
            finish();
        }


    }

    @Override
    public void displayAllUsers(List<User> user) {

    }

    @Override
    public void displayUser(User user) {
        this.user=user;
        Log.d(TAG,"displayUser uid: "+user.getUid());
        cookService=new CookServiceImpl();
        cookService.read(user.getUid(),RoleActivity.this);
        Intent i = new Intent(this,CookActivity.class);
        startActivity(i);

    }

    @Override
    public void displayAllCooks(List<Cook> cook) {

    }

    @Override
    public void displayCook(Cook cook) {
    if(cook==null){
        Cook newcook=new Cook(user.getUid(),user.getName(),user.getPhoneNumber(),
                user.getAddressLine1(),user.getAddressLine2(),user.getState(),user.getCountry()
                ,user.getZipcode(),user.getProfilePicPath(),user.getEmail());
        cookService.add(newcook);
    }
    }
}
