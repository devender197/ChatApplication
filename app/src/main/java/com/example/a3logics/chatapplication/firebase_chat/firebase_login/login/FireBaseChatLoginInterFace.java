package com.example.a3logics.chatapplication.firebase_chat.firebase_login.login;


import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.User;

/**
 * Created by a3logics on 19/3/18.
 */

public interface FireBaseChatLoginInterFace {

    void onSuccessFulLogin();
    void onUnSuccessFulLogin(Exception e);
    void onSuccessFulCreateUser();
    void onUnSuccessFulCreateUser(Exception e);
    void onSuccessFulPasswdReset();
    void onUnSuccessFulPasswdReset();
    void onUnAuthorizedAccess();
    void isEmailVerificationSend();
    void sendEmailVerification();
    void saveValue(User user);
}
