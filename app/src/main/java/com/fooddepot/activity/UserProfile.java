package com.fooddepot.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fooddepot.R;
import com.fooddepot.activity.order.OrderStatusActivity;
import com.fooddepot.activity.order.UserOrderListActivity;
import com.fooddepot.service.api.UserService;
import com.fooddepot.service.impl.CookServiceImpl;
import com.fooddepot.service.impl.UserServiceImpl;
import com.fooddepot.storage.impl.ProfilePicServiceImpl;
import com.fooddepot.ui.api.UIUserService;
import com.fooddepot.vo.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfile extends Fragment implements UIUserService{
    private Button orderList,updateProfile,saveProfile;
    private User user;
    private UserService userService=null;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    EditText  userAddressLine1, userAddressLine2, userEmail, userState, userCountry, userZip;
    ImageView userProfilePic;
    TextView userName,userPhone;
    ProfilePicServiceImpl profilePicService;
    Uri selectedImage;

    private static final int RESULT_LOAD_IMAGE=0;


    public static UserProfile newInstance() {
        UserProfile fragment = new UserProfile();
        return fragment;
    }



    public UserProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userService= new UserServiceImpl();
        userService.read(currentUser.getUid(),UserProfile.this);

        userName =(TextView) getView().findViewById(R.id.name_edtxt);
        userAddressLine1 =(EditText)getView().findViewById(R.id.adrsline1_edtxt);
        userAddressLine2 =(EditText)getView().findViewById(R.id.adrsline2_edtxt);
        userEmail =(EditText)getView().findViewById(R.id.email_edtxt);
        userState =(EditText)getView().findViewById(R.id.state_edtxt);
        userCountry =(EditText)getView().findViewById(R.id.cntry_edtxt);
        userZip =(EditText)getView().findViewById(R.id.zip_edtxt);
        userPhone=(TextView) getView().findViewById(R.id.phone_edtxt);
        userProfilePic =(ImageView)getView().findViewById(R.id.profile_img);

        profilePicService = new ProfilePicServiceImpl();


//        updateProfile=(Button)getView().findViewById(R.id.profilepic_btn);

        uploadprofilePic();
        saveProfile();
        getOrderList();
    }

    public void uploadprofilePic(){
        updateProfile =(Button)getView().findViewById(R.id.profilepic_btn);

        updateProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent galeryIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeryIntent,RESULT_LOAD_IMAGE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data !=null){
            selectedImage=data.getData();
            userProfilePic.setImageURI(selectedImage);




        }
    }


    public void saveProfile(){
        saveProfile =(Button)getView().findViewById(R.id.saveProfile_btn);
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setAddressLine1(userAddressLine1.getText().toString());
                user.setAddressLine2(userAddressLine2.getText().toString());
                user.setEmail(userEmail.getText().toString());
                user.setState(userState.getText().toString());
                user.setCountry(userCountry.getText().toString());
                user.setZipcode(userZip.getText().toString());
                if(selectedImage!=null){
                    user.setProfilePicPath("user/"+currentUser.getUid()+".jpg");
                    profilePicService.uploadFromUri(selectedImage,"user",currentUser.getUid()+".jpg");
                }

                userService.update(user);
            }
        });

    }



    public void getOrderList(){
        orderList =(Button)getView().findViewById(R.id.orderList);
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserOrderListActivity.class);
                intent.putExtra("USER_ID", user.getUid());
                startActivity(intent);
            }
        });

    }

    @Override
    public void displayAllUsers(List<User> user) {

    }

    @Override
    public void displayUser(User user) {
        this.user=user;
        userName.setText(user.getName());
        userPhone.setText(user.getPhoneNumber());
        userAddressLine1.setText(user.getAddressLine1());
        userAddressLine2.setText(user.getAddressLine2());
        userEmail.setText(user.getEmail());
        userState.setText(user.getState());
        userCountry.setText(user.getCountry());
        userZip.setText(user.getZipcode());
        if(user.getProfilePicPath()==null)
            userProfilePic.setImageResource(R.drawable.profile_pic);
        else
            profilePicService.loadProfilePic(getActivity(),userProfilePic,user.getProfilePicPath());



    }
}
