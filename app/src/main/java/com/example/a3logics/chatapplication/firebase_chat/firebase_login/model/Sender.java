package com.example.a3logics.chatapplication.firebase_chat.firebase_login.model;

import android.support.annotation.NonNull;

public class Sender implements Comparable<Sender> {
    private String userName;
    private String imageUrl;
    private String latestMessage;
    private String messageType;
    private String messageTimeStamp;
    private String isSeenStatus;
    private String roomID;

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageTimeStamp() {
        return messageTimeStamp;
    }

    public void setMessageTimeStamp(String messageTimeStamp) {
        this.messageTimeStamp = messageTimeStamp;
    }

    public String getIsSeenStatus() {
        return isSeenStatus;
    }

    public void setIsSeenStatus(String isSeenStatus) {
        this.isSeenStatus = isSeenStatus;
    }

    @Override
    public int compareTo(@NonNull Sender o) {
        if(this.getMessageTimeStamp() == o.getMessageTimeStamp()){
            return 0;
        } else if(this.getMessageTimeStamp() == o.getMessageTimeStamp()) {
            return 1;
        }else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        Sender sender = (Sender) obj;
        return sender.getRoomID().equalsIgnoreCase(this.getRoomID());
    }

    @Override
    public int hashCode() {
        return this.getRoomID().hashCode();
    }
}
