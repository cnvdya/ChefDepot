package com.fooddepot.activity.cook;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fooddepot.R;
import com.fooddepot.activity.order.OrderStatusActivity;
import com.fooddepot.service.api.CookService;
import com.fooddepot.service.impl.CookServiceImpl;
import com.fooddepot.service.impl.ItemServiceImpl;
import com.fooddepot.storage.impl.ProfilePicServiceImpl;
import com.fooddepot.ui.api.UICookService;
import com.fooddepot.vo.Cook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CookProfileFragment extends Fragment implements UICookService {


    EditText description, nickname,addressLine1,addressLine2,state,country,zipcode;
    Button profilepic_btn,saveProfile_btn, qrBtn;
    ImageView profileImg;
    private static final int RESULT_LOAD_IMAGE=2;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    Cook cook;
    CookService cookService = null;
    ProfilePicServiceImpl profilePicService;
    Uri selectedImage;

    public CookProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cook_profile, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        cookService=new CookServiceImpl();
        cookService.read(currentUser.getUid(),CookProfileFragment.this);

        description = (EditText) getView().findViewById(R.id.desc_edtxt);
        description.setMovementMethod(new ScrollingMovementMethod());
        nickname=(EditText)getView().findViewById(R.id.nickName_edtxt);
        addressLine1=(EditText)getView().findViewById(R.id.adrsline1_edtxt);
        addressLine2=(EditText)getView().findViewById(R.id.adrsline2_edtxt);
        state=(EditText)getView().findViewById(R.id.state_edtxt);
        country=(EditText)getView().findViewById(R.id.cntry_edtxt);
        zipcode=(EditText)getView().findViewById(R.id.zip_edtxt);
        profilePicService = new ProfilePicServiceImpl();
        uploadprofilePic();
        saveProfile();

    }

    public void saveProfile(){
        saveProfile_btn =(Button)getView().findViewById(R.id.saveProfile_btn);
        saveProfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookService=new CookServiceImpl();
                cook.setNickName(nickname.getText().toString());
                cook.setAddressLine1(addressLine1.getText().toString());
                cook.setAddressLine2(addressLine2.getText().toString());
                cook.setState(state.getText().toString());
                cook.setCountry(country.getText().toString());
                cook.setZipcode(zipcode.getText().toString());
                cook.setDesc(description.getText().toString());
                //cook.setProfilePicPath("profilepicpath");
                cook.setEmail("email");

                if(selectedImage!=null){
                    cook.setProfilePicPath("cook/"+currentUser.getUid()+".jpg");
                    profilePicService.uploadFromUri(selectedImage,"cook",currentUser.getUid()+".jpg");
                }

                cookService.update(cook);
                Toast.makeText(getActivity(), "Profile Updated successfully ", Toast.LENGTH_LONG).show();
            }
        });

    }



    public void uploadprofilePic(){
        profilepic_btn =(Button)getView().findViewById(R.id.profilepic_btn);
        profileImg =(ImageView)getView().findViewById(R.id.profile_img);
        profilepic_btn.setOnClickListener(new View.OnClickListener(){
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
            profileImg.setImageURI(selectedImage);

        }
    }

    @Override
    public void displayAllCooks(List<Cook> cook) {

    }

    @Override
    public void displayCook(Cook cook) {
        this.cook=cook;
        description.setText(cook.getDesc());
        nickname.setText(cook.getNickName());
        addressLine1.setText(cook.getAddressLine1());
        addressLine2.setText(cook.getAddressLine2());
        state.setText(cook.getState());
        country.setText(cook.getCountry());
        zipcode.setText(cook.getZipcode());

        if(cook.getProfilePicPath()==null)
            profileImg.setImageResource(R.drawable.profile_pic);
        else
            profilePicService.loadProfilePic(getActivity(),profileImg,cook.getProfilePicPath());



    }
}
