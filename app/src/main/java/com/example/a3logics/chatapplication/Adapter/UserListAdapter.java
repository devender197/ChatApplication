package com.example.a3logics.chatapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a3logics.chatapplication.MainActivity;
import com.example.a3logics.chatapplication.R;
import com.example.a3logics.chatapplication.chatApplication.ChatActivity;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewAdapter> {
    ArrayList<String> mUserList;
    Context mContext;
    public static final String USERNAME = "username";
    public static final String ROOM_ID = "room_id";
    public UserListAdapter(ArrayList<String> userList, Context context){
        mUserList =  userList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
        return new UserListAdapter.ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewAdapter viewAdapter, int i) {
        viewAdapter.username.setText(mUserList.get(i));
        viewAdapter.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mContext).checkChatRoom(viewAdapter.username.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mUserList == null){
            return 0;
        }else{
            return mUserList.size();
        }
    }

    class ViewAdapter extends RecyclerView.ViewHolder{
            TextView username;
        public ViewAdapter(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);
        }
    }
}
