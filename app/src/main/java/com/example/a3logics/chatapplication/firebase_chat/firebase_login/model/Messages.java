package com.example.a3logics.chatapplication.firebase_chat.firebase_login.model;


import android.support.annotation.NonNull;

public class Messages implements Comparable<Messages> {

    private String  senderName;
    private String  uid;
    private String  createdDate;
    private long    timeStamp;
    private String  text;
    private String  imageUrlA;
    private String  imageUrlI;
    private String  pdfUrlA;
    private String  pdfUrlI;
    private String  userRole;
    private boolean isSystemText;
    private String  messageType;
    private boolean isSeen;
    private String  fileUrlA;
    public String   email;
    public String   senderId;
    public String   receiverUid;
    public String   phoneNumber;
    public String   fuid;
    public String   deviceToken;
    public String   firebaseUId;
    public String   audioURL_a;
    public String   audioURL_i;
    public String   localAudioUrl;
    public String   readStatus = "sending";
    public String   receiverNumber;
    public String   senderNumber;
    public String   receiverName;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPdfUrlA() {
        return pdfUrlA;
    }

    public void setPdfUrlA(String pdfUrlA) {
        this.pdfUrlA = pdfUrlA;
    }

    public String getPdfUrlI() {
        return pdfUrlI;
    }

    public void setPdfUrlI(String pdfUrlI) {
        this.pdfUrlI = pdfUrlI;
    }

    public String getFuid() {
        return fuid;
    }

    public void setFuid(String fuid) {
        this.fuid = fuid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getFirebaseUId() {
        return firebaseUId;
    }

    public void setFirebaseUId(String firebaseUId) {
        this.firebaseUId = firebaseUId;
    }

    public String getAudioURL_a() {
        return audioURL_a;
    }

    public void setAudioURL_a(String audioURL_a) {
        this.audioURL_a = audioURL_a;
    }

    public String getAudioURL_i() {
        return audioURL_i;
    }

    public void setAudioURL_i(String audioURL_i) {
        this.audioURL_i = audioURL_i;
    }

    public String getLocalAudioUrl() {
        return localAudioUrl;
    }

    public void setLocalAudioUrl(String localAudioUrl) {
        this.localAudioUrl = localAudioUrl;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getFileUrlA() {
        return fileUrlA;
    }

    public void setFileUrlA(String fileUrlA) {
        this.fileUrlA = fileUrlA;
    }

    public String getMessageType() {
        return messageType;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public boolean getIsSystemText() {
        return isSystemText;
    }

    public void setIsSystemText(boolean isSystemText) {
        this.isSystemText = isSystemText;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getImageUrlA() {
        return imageUrlA;
    }

    public void setImageUrlA(String imageUrlA) {
        this.imageUrlA = imageUrlA;
    }

    public String getImageUrlI() {
        return imageUrlI;
    }

    public void setImageUrlI(String imageUrlI) {
        this.imageUrlI = imageUrlI;
    }


    @Override
    public int compareTo(@NonNull Messages o) {
        System.out.println("this-->"+timeStamp+"and object --->"+o.timeStamp+"and their equal value--->"+(timeStamp == o.getTimeStamp())+","+(timeStamp == o.timeStamp));
            if(this.timeStamp == o.getTimeStamp()){
                return 0;
            } else if(this.timeStamp == o.getTimeStamp()) {
                return 1;
            }else {
                return -1;
            }
    }
}