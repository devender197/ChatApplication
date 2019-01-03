package com.example.a3logics.chatapplication.firebase_chat.firebase_login.model;

public class Receiver {
    private String userName;
    private String imageUrl;
    private String latestMessage;
    private String messageType;
    private String messageTimeStamp;
    private String isSeenStatus;

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
}

