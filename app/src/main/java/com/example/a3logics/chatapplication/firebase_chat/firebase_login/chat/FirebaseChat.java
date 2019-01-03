package com.example.a3logics.chatapplication.firebase_chat.firebase_login.chat;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.a3logics.chatapplication.firebase_chat.firebase_login.ApiConstant;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.Messages;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.Receiver;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.Sender;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by a3logics on 20/3/18.
 */

public class FirebaseChat {
    private static final String TAG = FirebaseChat.class.getSimpleName();
    private long timestamps;
    private FirebaseChatInterface mFirebaseChatInterface;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(ApiConstant.FIREBASE_STORAGE_PATH);
    private String messageKey;
    private long imageTimeStamp;
    private String roomID;
    private String sender = "devender197";
    private String receiver = "devender198";


    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public FirebaseChat(FirebaseChatInterface firebaseChatInterface) {
        mFirebaseChatInterface = firebaseChatInterface;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * Getting member joining date
     *
     * @param firebaseConnectionPath
     * @param roomid
     */
    public void getFirebaseMessage(final String firebaseConnectionPath, String roomid) {

        FirebaseDatabase.getInstance().getReference().child(firebaseConnectionPath).orderByChild(ApiConstant.FIREBASE_TIMESTAMP).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFirebaseChatInterface.activityActionOnFirebaseChildAdded(null, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getDetails());
                mFirebaseChatInterface.activityActionOnFirebaseChildAdded(null, null);
            }
        });


        /*FirebaseDatabase.getInstance().getReference().child(ApiConstant.FIREBASE_CHATBOX + roomid + ApiConstant.FIREBASE_MEMBER_KEY).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                    mapMessage.size();
                    Set entrySet = mapMessage.entrySet();
                    // Obtaining an iterator for the entry set
                    Iterator it = entrySet.iterator();
                    // Iterate through HashMap entries(Key-Value pairs)
                    while (it.hasNext()) {
                        Map.Entry me = (Map.Entry) it.next();
                        String ss = ((HashMap) (mapMessage.get(me.getKey()))).get(ApiConstant.FIREBASE_USERNAME_KEY).toString();
                        //Log.i("username",ss);
                        if (ss.equals(userPrimaryMail)) {
                            timestamps = Long.parseLong(((HashMap) (mapMessage.get(me.getKey()))).get(ApiConstant.FIREBASE_JOINING_DATE_KEY).toString());
                            //Log.i("timestamps",timestamps+"");
                            break;
                        } else {
                            continue;
                        }
                    }
                    callChat(firebaseConnectionPath);
                } catch (Exception e) {
                    callChat(firebaseConnectionPath);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        callChat(firebaseConnectionPath+roomid);

    }
    public void getChatUserList(final String firebaseConnectionPath, final String caseString) {

        FirebaseDatabase.getInstance().getReference().child(firebaseConnectionPath).orderByChild(ApiConstant.FIREBASE_TIMESTAMP).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFirebaseChatInterface.activityActionOnFirebaseChildAdded(dataSnapshot, caseString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getDetails());
                mFirebaseChatInterface.activityActionOnFirebaseCancelled(databaseError);
            }
        });
    }

    public void getChatUserList(final String firebaseConnectionPath) {

        FirebaseDatabase.getInstance().getReference().child(firebaseConnectionPath).orderByChild(ApiConstant.FIREBASE_TIMESTAMP)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        mFirebaseChatInterface.activityActionOnFirebaseChildAdded(dataSnapshot, "user");
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    /**
     * Following function will be used to get member detail from the chat room using the chat room
     * id (ticketRoomId).
     *
     * @param ticketRoomId ID associated with the chat room
     * @param UID          user firebase ID
     */
    public void getMemberDetailFromChatRoom(final String ticketRoomId, String UID) {
        String str = ticketRoomId + "/" + ApiConstant.FIREBASE_MEMBERS + "/" + UID;
        System.out.println(str);
        mDatabase.child(ApiConstant.FIREBASE_TICKETS_NAME).child(ticketRoomId).child(ApiConstant.FIREBASE_MEMBERS).child(UID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            if (dataSnapshot != null) {
                                HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                                String count = (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_MEMBERS_UNREAD_COUNT) + "" : null);
                                String UID   = (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_UID_NAME) + "" : null);
                                mFirebaseChatInterface.activityActionOnFirebaseMemberChangeListener(count, ticketRoomId,UID);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if (databaseError != null) {
                            mFirebaseChatInterface.activityActionOnFirebaseCancelled(databaseError);
                        }
                    }
                });
        childChangeListener(ticketRoomId);
    }

    /**
     * Following function used to close the child Event listener
     * @param childEventListener reference of childEventListener
     */
    public void removeListener(ChildEventListener childEventListener){
        if(childEventListener != null) {
            mDatabase.removeEventListener(childEventListener);
        }
    }

    public ChildEventListener childChangeListener(final String ticketRoomId){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    if (dataSnapshot != null) {
                        HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                        String count = (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_MEMBERS_UNREAD_COUNT) + "" : null);
                        String UID   = (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_UID_NAME) + "" : null);
                        mFirebaseChatInterface.activityActionOnFirebaseMemberChangeListener(count, ticketRoomId,UID);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.child(ApiConstant.FIREBASE_TICKETS_NAME).child(ticketRoomId).child(ApiConstant.FIREBASE_MEMBERS).addChildEventListener(childEventListener);
        return childEventListener;
    }


    public void updateValueAtFirebase(String firebaseConnectionPath,String childKey, String childValue) {
        try {
            Long childValueLong  = Long.parseLong(childValue);
            mDatabase.child(firebaseConnectionPath).child(childKey).setValue(childValueLong);
        }catch (Exception e){
            mDatabase.child(firebaseConnectionPath).child(childKey).setValue(childValue);
        }
    }

    /**
     * Function is created to fetch message from given path in firebase real time database
     *
     * @param firebaseConnectionPath message path in firebase data base
     */
    public void callChat(String firebaseConnectionPath) {
        FirebaseDatabase.getInstance().getReference().child(firebaseConnectionPath).orderByChild(ApiConstant.FIREBASE_TIMESTAMP).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                    Messages newMessage = new Messages();
                    String imageUrl = (String) (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_IMAGE_URLA) : null);
                    String audioUrl = (String) (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_AUDIO_URLA) : null);
                    String pdfUrl = (String) (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_PDF_URLA) : null);
                    if (imageUrl != null && imageUrl.equals(ApiConstant.FIREBASE_NOT_SET)) {
                        messageKey = dataSnapshot.getKey();
                        imageTimeStamp = Long.parseLong(mapMessage.get(ApiConstant.FIREBASE_TIMESTAMP).toString());
                    }else if(audioUrl != null && audioUrl.equals(ApiConstant.FIREBASE_NOT_SET)){
                        messageKey = dataSnapshot.getKey();
                        imageTimeStamp = Long.parseLong(mapMessage.get(ApiConstant.FIREBASE_TIMESTAMP).toString());
                    }else if(pdfUrl != null && pdfUrl.equals(ApiConstant.FIREBASE_NOT_SET)){
                        messageKey = dataSnapshot.getKey();
                        imageTimeStamp = Long.parseLong(mapMessage.get(ApiConstant.FIREBASE_TIMESTAMP).toString());
                    }
                    mFirebaseChatInterface.activityActionOnFirebaseChildAdded(dataSnapshot, s);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    mFirebaseChatInterface.activityActionOnFirebaseChildChanged(dataSnapshot, s);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    mFirebaseChatInterface.activityActionOnFirebaseChildRemoved(dataSnapshot);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    mFirebaseChatInterface.activityActionOnFirebaseChildMoved(dataSnapshot, s);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError != null) {
                    mFirebaseChatInterface.activityActionOnFirebaseCancelled(databaseError);
                }
            }

        });
    }

    /**
     * Function is created to fetch message from given path in firebase real time database
     *
     * @param firebaseConnectionPath message path in firebase data base
     * @param newMessage             message which will be send to firebase
     */
    public void writeMessageFirebase(String firebaseConnectionPath, Messages newMessage) {
        FirebaseDatabase.getInstance().getReference().child("ChatBox/"+roomID).push().setValue(newMessage);
        final Sender senderobj = new Sender();
        final Receiver receiverObj = new Receiver();
        senderobj.setUserName(sender);
        receiverObj.setUserName(receiver);
        senderobj.setMessageType(newMessage.getMessageType());
        receiverObj.setMessageType(newMessage.getMessageType());
        senderobj.setMessageTimeStamp(newMessage.getTimeStamp()+"");
        receiverObj.setMessageTimeStamp(newMessage.getTimeStamp()+"");
        String textMessage = "";
        if("Text".equalsIgnoreCase(newMessage.getMessageType())){
            textMessage = newMessage.getText();
        }else if(newMessage.getImageUrlA() != null){
            textMessage = "Photo";
        }else if(newMessage.getAudioURL_a() != null){
            textMessage = "Audio";
        }
        senderobj.setLatestMessage(textMessage);
        receiverObj.setLatestMessage(textMessage);

        FirebaseDatabase.getInstance().getReference().child("ChatBox/"+roomID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                FirebaseDatabase.getInstance().getReference().child("user/"+sender+"/chatRoom/"+roomID).setValue(receiverObj);
                FirebaseDatabase.getInstance().getReference().child("user/"+receiver+"/chatRoom/"+roomID).setValue(senderobj);
                System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void updateMessageWithKeyFirebase(String firebaseConnectionPath, String childKey, String childValue){
        mDatabase.child(firebaseConnectionPath).child(childKey).setValue(childValue).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Function is created to fetch message from given path in firebase real time database
     *
     * @param firebaseConnectionPath message path in firebase data base
     * @param newMessage             message which will be send to firebase
     */
    public void updateMessageFirebase(String firebaseConnectionPath, Messages newMessage, String messageKey) {
        //FirebaseDatabase.getInstance().getReference().child(firebaseConnectionPath+messageKey).push().setValue(newMessage);
        /*String key = mDatabase.child("posts").push().getKey();

        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);*/
        newMessage.setTimeStamp(imageTimeStamp);
        newMessage.setCreatedDate(imageTimeStamp + "");
        mDatabase.child("ChatBox/"+roomID).child(messageKey).setValue(newMessage);
    }




    /**
     * Function is create to upload file to firebase server and get file url on server
     *
     * @param fileExtension Extension of upload file
     * @param bitmap        Image bitmap which is to be upload on server
     */
    public void uploadImageFileFirebase(Bitmap bitmap, String fileExtension, final String localFileUrl) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
        storageRef.child(UUID.randomUUID().toString() + "." + fileExtension)
                .putBytes(byteArrayOutputStream.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getDownloadUrl() != null) {
                            mFirebaseChatInterface.activityActionOnFirebaseFileUpload(taskSnapshot, messageKey);
//                            File file = new File(localFileUrl);
//                            if(file != null) {
//                                file.delete();
//                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
        mFirebaseChatInterface.activityActionOnFirebaseFileUploadNew(null, localFileUrl);
    }

    public void uploadAudioFile(String filePath){
        if(filePath != null) {
            Uri file = Uri.fromFile(new File(filePath));
            StorageReference riversRef = storageRef.child(file.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    exception.printStackTrace();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getDownloadUrl() != null) {
                        mFirebaseChatInterface.activityActionOnFirebaseAudioFileUpload(taskSnapshot, messageKey);

                    }
                }
            });
            mFirebaseChatInterface.activityActionOnFirebaseAudioFileUpload();
        }

    }


    public void uploadFile(Uri filePath){
        if(filePath != null) {
            StorageReference riversRef = storageRef.child(filePath.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(filePath);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    exception.printStackTrace();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot.getDownloadUrl() != null) {
                        mFirebaseChatInterface.activityActionOnFirebasePDFFileUpload(taskSnapshot, messageKey);

                    }
                }
            });
            mFirebaseChatInterface.activityActionOnFirebasePDFFileUpload();
        }

    }


}
