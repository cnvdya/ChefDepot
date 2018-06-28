package com.fooddepot.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.fooddepot.R;

public class RegisterActivity extends AppCompatActivity {
    EditText phone, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone =(EditText)findViewById(R.id.phone_editxt);
        name =(EditText)findViewById(R.id.name_editxt);
    }

    public void getVerificationCode(View view){
        Intent i = new Intent(this,OTPVerificationActivity.class);
        i.putExtra("NAME",name.getText().toString());
        i.putExtra("PHONE",phone.getText().toString());
        Log.d("RegisterActivity","Name: "+name.getText().toString());
        Log.d("RegisterActivity","Phone:" +phone.getText().toString());
        startActivity(i);

    }
}




