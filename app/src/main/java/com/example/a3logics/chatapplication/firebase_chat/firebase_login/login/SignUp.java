package com.example.a3logics.chatapplication.firebase_chat.firebase_login.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a3logics.chatapplication.Common.PreferenceManager;
import com.example.a3logics.chatapplication.Common.Utils;
import com.example.a3logics.chatapplication.R;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.chat.FirebaseChat;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.User;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class SignUp extends AppCompatActivity implements FireBaseChatLoginInterFace, View.OnClickListener {

    private EditText          mUserName, mPassWord;
    private Button            mSignUp;
    private Context           mContext;
    private FireBaseChatLogin mFirebaseChat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        bindView();
    }

    private void bindView(){
        mUserName     = findViewById(R.id.email_id);
        mPassWord     = findViewById(R.id.password);
        mSignUp       = findViewById(R.id.btn_sign_up);
        mContext      = SignUp.this;
        mFirebaseChat = new FireBaseChatLogin(this);
        mSignUp.setOnClickListener(this);
    }


    @Override
    public void onSuccessFulLogin() {

    }

    @Override
    public void onUnSuccessFulLogin(Exception e) {

    }

    @Override
    public void onSuccessFulCreateUser() {
        Intent intent = new Intent(SignUp.this, SignIn.class);
        startActivity(intent);
        finish();
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

    }

    @Override
    public void isEmailVerificationSend() {
        PreferenceManager.putOnSharedPreference(SignIn.isEmailVerificationSend,true,mContext);
        Utils.showCustomAlertDialog(mContext,"Email Verification",
                "Email verification has been sent to server");
    }

    @Override
    public void sendEmailVerification() {
        if(!PreferenceManager.getBooleanFromSharedPreferences(SignIn.isEmailVerificationSend,mContext)){
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
               mFirebaseChat.createUser(mUserName.getText().toString(),mPassWord.getText().toString());
               break;
        }
    }
}
