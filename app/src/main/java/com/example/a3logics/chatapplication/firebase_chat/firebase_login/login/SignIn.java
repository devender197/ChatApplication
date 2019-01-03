package com.example.a3logics.chatapplication.firebase_chat.firebase_login.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3logics.chatapplication.ChatUserList;
import com.example.a3logics.chatapplication.Common.PreferenceManager;
import com.example.a3logics.chatapplication.Common.Utils;
import com.example.a3logics.chatapplication.MainActivity;
import com.example.a3logics.chatapplication.R;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.User;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class SignIn extends AppCompatActivity implements FireBaseChatLoginInterFace, View.OnClickListener {

    private EditText mUserName, mPassWord;
    private Button mSignUp;
    private Context mContext;
    private FireBaseChatLogin mFirebaseChat;
    private TextView mbtnForgetPassword;
    private TextView mbtnRegister;
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String isEmailVerificationSend = "is_email_verification_send";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        bindView();
    }

    private void bindView(){
        mUserName           = findViewById(R.id.email_id);
        mPassWord           = findViewById(R.id.password);
        mSignUp             = findViewById(R.id.btn_sign_up);
        mContext            = SignIn.this;
        mbtnForgetPassword  = findViewById(R.id.forget_password);
        mbtnRegister        = findViewById(R.id.register);
        mFirebaseChat       = new FireBaseChatLogin(this);
        mSignUp.setText(getString(R.string.txt_sign_in));
        mbtnForgetPassword.setVisibility(View.VISIBLE);
        mbtnRegister.setVisibility(View.VISIBLE);
        mbtnRegister.setOnClickListener(this);
        mbtnForgetPassword.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        String userName = PreferenceManager.getStringFromSharedPreferences(USERNAME,mContext);
        String passWord = PreferenceManager.getStringFromSharedPreferences(PASSWORD,mContext);
        if(userName != null && !userName.isEmpty() && passWord != null && !passWord.isEmpty()){
            mFirebaseChat.signIn(userName,passWord);
        }
    }


    @Override
    public void onSuccessFulLogin() {
        String username = PreferenceManager.getStringFromSharedPreferences(USERNAME,mContext);
        String password = PreferenceManager.getStringFromSharedPreferences(PASSWORD,mContext);
        if(username == null || username.isEmpty()) {
            PreferenceManager.putOnSharedPreference(USERNAME, mUserName.getText().toString(), mContext);
            PreferenceManager.putOnSharedPreference(PASSWORD, mPassWord.getText().toString(), mContext);
        }
        Toast.makeText(mContext, "Signed In Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignIn.this, ChatUserList.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onUnSuccessFulLogin(Exception e) {
            if(e instanceof FirebaseAuthInvalidCredentialsException){
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }else if(e instanceof UnSuccessfulException){
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onSuccessFulCreateUser() {

    }

    @Override
    public void onUnSuccessFulCreateUser(Exception e) {
        if(e instanceof FirebaseAuthInvalidCredentialsException){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }else if(e instanceof UnSuccessfulException){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessFulPasswdReset() {

    }

    @Override
    public void onUnSuccessFulPasswdReset() {

    }

    @Override
    public void onUnAuthorizedAccess() {
        if(mContext != null){
            Utils.showCustomAlertDialog(mContext,"Verification Pending","Email Verification Pending");
        }
    }

    @Override
    public void isEmailVerificationSend() {
        PreferenceManager.putOnSharedPreference(SignIn.isEmailVerificationSend,true,mContext);
        Utils.showCustomAlertDialog(mContext,"Email Verification",
                "Email verification has been sent to server");
    }

    @Override
    public void sendEmailVerification() {
        if(!PreferenceManager.getBooleanFromSharedPreferences(isEmailVerificationSend,mContext)){
            mFirebaseChat.getUser().sendEmailVerification();
        }else{
            Utils.showCustomAlertDialog(mContext,"Email Verification",
                    "Email verification has been sent to server");
        }
    }

    @Override
    public void saveValue(User user) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_up:
                 mFirebaseChat.signIn(mUserName.getText().toString(),mPassWord.getText().toString());
                 break;
            case R.id.forget_password:

                 break;
            case R.id.register:
                 Intent intent = new Intent(SignIn.this, SignUp.class);
                 startActivity(intent);
                 break;
        }
    }
}
