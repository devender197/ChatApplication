package com.example.a3logics.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.a3logics.chatapplication.Adapter.UserListAdapter;
import com.example.a3logics.chatapplication.Common.PreferenceManager;
import com.example.a3logics.chatapplication.chatApplication.ChatActivity;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.ApiConstant;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.chat.FirebaseChat;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.chat.FirebaseChatInterface;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.login.FireBaseChatLogin;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.login.FireBaseChatLoginInterFace;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.login.SignIn;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.User;
import com.google.android.gms.common.api.Api;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements FirebaseChatInterface {
    Button button;
    RecyclerView recyclerView;
    FirebaseChat firebaseChat;
    UserListAdapter userListAdapter;
    String senderUserName;
    String receiverUserName;
    Context mContext;
    FloatingActionButton mLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.user_list_recycler);
        firebaseChat = new FirebaseChat(this);
        mLogout = findViewById(R.id.log_out);
        mContext = MainActivity.this;
        firebaseChat.getChatUserList("user/","user");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        String user = PreferenceManager.getStringFromSharedPreferences(SignIn.USERNAME,this);
        senderUserName = user.substring(0, user.indexOf("@"));
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
                finish();
                PreferenceManager.clear(mContext);
            }
        });

    }

    public void checkChatRoom(String str){
        receiverUserName = str;
        String user = PreferenceManager.getStringFromSharedPreferences(SignIn.USERNAME,this);
        String senderUserName  = user.substring(0, user.indexOf("@"));
        firebaseChat.getChatUserList("user/"+senderUserName+"/chatRoom","chatbox");
    }

    @Override
    public void activityActionOnFirebaseChildAdded(DataSnapshot dataSnapshot, String s) {
        HashMap mapMessage = (HashMap) dataSnapshot.getValue();
        String user = PreferenceManager.getStringFromSharedPreferences(SignIn.USERNAME,this);
        String senderUserName  = user.substring(0, user.indexOf("@"));
        if(s.equalsIgnoreCase("user")) {
            ArrayList<String> userList = new ArrayList<>();
            Set entrySet = mapMessage.entrySet();
            // Obtaining an iterator for the entry set
            Iterator it = entrySet.iterator();
            // Iterate through HashMap entries(Key-Value pairs)
            while (it.hasNext()) {
                Map.Entry me = (Map.Entry) it.next();
                String ss = ((HashMap) (mapMessage.get(me.getKey()))).get(ApiConstant.FIREBASE_USERNAME_KEY).toString();
                if (!ss.equalsIgnoreCase(senderUserName)) {
                    userList.add(ss);
                }
            }
            userListAdapter = new UserListAdapter(userList, this);
            recyclerView.setAdapter(userListAdapter);
        }else{
            ArrayList<String> chatBoxList = new ArrayList<>();
            Set entrySet = mapMessage.entrySet();
            // Obtaining an iterator for the entry set
            Iterator it = entrySet.iterator();
            // Iterate through HashMap entries(Key-Value pairs)
            while (it.hasNext()) {
                Map.Entry me = (Map.Entry) it.next();
                String ss = (String) me.getKey();
                chatBoxList.add(ss);
            }
            System.out.print(chatBoxList);
            setIntent(chatBoxList);
        }
    }

    void setIntent(ArrayList<String> strings){
        for(String str : strings){
            if(str.contains(receiverUserName)){
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(UserListAdapter.ROOM_ID,str);
                intent.putExtra(UserListAdapter.USERNAME,receiverUserName);
                startActivity(intent);
                break;
            }else{
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(UserListAdapter.USERNAME,receiverUserName);
                startActivity(intent);
                break;
            }
        }

    }

    @Override
    public void activityActionOnFirebaseChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void activityActionOnFirebaseChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void activityActionOnFirebaseChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void activityActionOnFirebaseCancelled(DatabaseError databaseError) {

    }

    @Override
    public void activityActionOnFirebaseFileUpload(UploadTask.TaskSnapshot taskSnapshot, String messageKey) {

    }

    @Override
    public void activityActionOnFirebaseFileUploadNew(UploadTask.TaskSnapshot taskSnapshot, String localUrl) {

    }

    @Override
    public void activityActionOnFirebaseMemberChangeListener(String count, String ticketId, String UID) {

    }

    @Override
    public void activityActionOnFirebaseAudioFileUpload() {

    }

    @Override
    public void activityActionOnFirebaseAudioFileUpload(UploadTask.TaskSnapshot taskSnapshot, String messageKey) {

    }

    @Override
    public void activityActionOnFirebasePDFFileUpload() {

    }

    @Override
    public void activityActionOnFirebasePDFFileUpload(UploadTask.TaskSnapshot taskSnapshot, String messageKey) {

    }
}
