package com.example.a3logics.chatapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a3logics.chatapplication.R;
import com.example.a3logics.chatapplication.chatApplication.ChatActivity;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.Messages;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.Sender;

import java.util.ArrayList;

public class chatUserListAdapter extends RecyclerView.Adapter<chatUserListAdapter.ChatUserListAdapterViewHolder>   {

    ArrayList<Sender> senderArrayList;
    Context mContext;
    public chatUserListAdapter(ArrayList<Sender> senders, Context context){
        senderArrayList = senders;
        mContext        = context;
    }
    @NonNull
    @Override
    public ChatUserListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_user_list, viewGroup, false);
        return new ChatUserListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUserListAdapterViewHolder chatUserListAdapterViewHolder, final int position) {
        ChatUserListAdapterViewHolder holder = chatUserListAdapterViewHolder;
        holder.messageIcon.setVisibility(View.VISIBLE);
        holder.message.setText(senderArrayList.get(position).getLatestMessage());
        holder.userName.setText(senderArrayList.get(position).getUserName());
        if("Photo".equalsIgnoreCase(senderArrayList.get(position).getLatestMessage())){
            holder.messageIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_photo_camera_black_24dp));
        }else if("Audio".equalsIgnoreCase(senderArrayList.get(position).getLatestMessage())){
            holder.messageIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
        }else {
            holder.messageIcon.setVisibility(View.GONE);
        }
        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra(UserListAdapter.ROOM_ID,senderArrayList.get(position).getRoomID());
                intent.putExtra(UserListAdapter.USERNAME,senderArrayList.get(position).getUserName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(senderArrayList == null){
            return 0;
        }else {
            return senderArrayList.size();
        }
    }

    public class ChatUserListAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView userName, message;
        ImageView userImage, messageIcon;
        LinearLayout mParentLayout;
        public ChatUserListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            userName    = itemView.findViewById(R.id.userName);
            message     = itemView.findViewById(R.id.message);
            userImage   = itemView.findViewById(R.id.user_image);
            messageIcon = itemView.findViewById(R.id.message_icon);
            mParentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public void add(Sender sender) {
        senderArrayList.add(sender);
        notifyItemInserted(senderArrayList.size() - 1);
    }

    public void update(Sender sender) {
        senderArrayList.add(sender);
        notifyItemInserted(senderArrayList.size() - 1);
    }
}
