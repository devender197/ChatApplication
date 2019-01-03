package com.example.a3logics.chatapplication.firebase_chat.firebase_login.login;

public class UnSuccessfulException extends Exception {


    @Override
    public String getMessage() {
        return "Operation Cannot be complete at moment. Please Try again Later";
    }
}
