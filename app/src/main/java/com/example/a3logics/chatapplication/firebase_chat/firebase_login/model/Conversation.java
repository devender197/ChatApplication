package com.example.a3logics.chatapplication.firebase_chat.firebase_login.model;

import java.util.ArrayList;


public class Conversation {
    private ArrayList<Messages> listMessageData;
    public Conversation(){
        listMessageData = new ArrayList<>();
    }

    public ArrayList<Messages> getListMessageData() {
        return listMessageData;
    }
}
