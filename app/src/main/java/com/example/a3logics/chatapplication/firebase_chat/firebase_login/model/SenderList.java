package com.example.a3logics.chatapplication.firebase_chat.firebase_login.model;

import java.util.ArrayList;

public class SenderList {
    public SenderList(){ }

    private ArrayList<Sender> senderArrayList;

    public ArrayList<Sender> getSenderArrayList() {
        return senderArrayList;
    }

    public void setSenderArrayList(ArrayList<Sender> senderArrayList) {
        this.senderArrayList = senderArrayList;
    }
}
