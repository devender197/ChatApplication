package com.example.a3logics.chatapplication.firebase_chat.firebase_login.model;



public class User {
    public String   name;
    public String   email;
    public String   avata;
    public Status   status;
    public Messages message;
    public String   chatRooms;


    public User(){
        status = new Status();
        message = new Messages();
        status.isOnline = false;
        status.timestamp = 0;

    }
}
