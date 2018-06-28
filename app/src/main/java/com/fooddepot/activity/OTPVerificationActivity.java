package com.fooddepot.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.fooddepot.R;
import com.fooddepot.service.api.UserService;
import com.fooddepot.service.impl.UserServiceImpl;
import com.fooddepot.ui.api.UIUserService;
import com.fooddepot.vo.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.graphics.Color.WHITE;

public class OTPVerificationActivity extends AppCompatActivity implements UIUserService {

    EditText otp;
    TextView message;
    Button btn,verify_btn;
    String name,phone,TAG="OTPVerificationActivity";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    private FirebaseAuth mAuth;
    PhoneAuthCredential credential;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        otp=(EditText)findViewById(R.id.otp_editxt);
        verify_btn=(Button)findViewById(R.id.verify_btn);
        message =(TextView)findViewById(R.id.message);
        mAuth= FirebaseAuth.getInstance();
        Intent i = getIntent();
        name=i.getStringExtra("NAME");
        phone=i.getStringExtra("PHONE");
        Log.d("OTPVErification","PHONE: "+phone);
        Log.d("OTPVErification","NAME: "+name);
        mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                credential=PhoneAuthProvider.getCredential(verificationId, code);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,
                60,
                TimeUnit.SECONDS,
                OTPVerificationActivity.this,
                mCallBack);


    }

    public void onClickVerify(View view){
        if(otp.getText().toString().equals("XYZ")){
            Intent i = new Intent(this,RoleActivity.class);
            i.putExtra("Status","Success");
            startActivity(i);
        }
        else{
            message.setText("Invalid Code. Please try again");
            Button btn = new Button(this);
            btn.setText("  Resend VerificationCode  ");
            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btn.setTextColor(WHITE);
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.otpVerificationlayout);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.topMargin=50;
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            lp.addRule(RelativeLayout.BELOW,message.getId());
            rl.addView(btn, lp);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            userService=new UserServiceImpl();
                            User regUser = new User(user.getUid(),name,phone);
                            userService.add(regUser);
                            Intent i = new Intent(OTPVerificationActivity.this,RoleActivity.class);
                            i.putExtra("Status","Success");
                            startActivity(i);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            message.setText("Invalid Code. Please try again");
                            Button btn = new Button(OTPVerificationActivity.this);
                            btn.setText("  Resend VerificationCode  ");
                            btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            btn.setTextColor(WHITE);
                            RelativeLayout rl = (RelativeLayout)findViewById(R.id.otpVerificationlayout);
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            lp.topMargin=50;
                            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                            lp.addRule(RelativeLayout.BELOW,message.getId());
                            rl.addView(btn, lp);
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    public void displayAllUsers(List<User> user) {

    }

    @Override
    public void displayUser(User user) {

    }
}


