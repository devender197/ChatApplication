package com.example.a3logics.chatapplication.firebase_chat.firebase_login.chat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.UploadTask;

/**
 * Created by a3logics on 20/3/18.
 */

public interface FirebaseChatInterface {

    void activityActionOnFirebaseChildAdded(DataSnapshot dataSnapshot, String s) ;

    void activityActionOnFirebaseChildChanged(DataSnapshot dataSnapshot, String s) ;

    void activityActionOnFirebaseChildRemoved(DataSnapshot dataSnapshot) ;

    void activityActionOnFirebaseChildMoved(DataSnapshot dataSnapshot, String s) ;

    void activityActionOnFirebaseCancelled(DatabaseError databaseError) ;

    void activityActionOnFirebaseFileUpload(UploadTask.TaskSnapshot taskSnapshot, String messageKey);

    void activityActionOnFirebaseFileUploadNew(UploadTask.TaskSnapshot taskSnapshot, String localUrl);

    void activityActionOnFirebaseMemberChangeListener(String count, String ticketId, String UID);

    void activityActionOnFirebaseAudioFileUpload();

    void activityActionOnFirebaseAudioFileUpload(UploadTask.TaskSnapshot taskSnapshot,String messageKey);


    void activityActionOnFirebasePDFFileUpload();

    void activityActionOnFirebasePDFFileUpload(UploadTask.TaskSnapshot taskSnapshot,String messageKey);
}
