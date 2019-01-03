package com.example.a3logics.chatapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.a3logics.chatapplication.AudioPlayer.AudioPlayerWidget;
import com.example.a3logics.chatapplication.AudioPlayer.FragmentPlayerInterface;
import com.example.a3logics.chatapplication.FullScreenImage;
import com.example.a3logics.chatapplication.R;
import com.example.a3logics.chatapplication.chatApplication.ChatActivity;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.Messages;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by a3logics on 7/2/18.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessagesViewHolder> implements FragmentPlayerInterface {


    private List<Messages> mMessageList;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private int selectedPosition = -1;
    private AudioPlayerWidget mAudioPlayerWidget;
    private List<AudioPlayerWidget> mediaPlayerList;
    private ArrayList<String> messageIdList;
    private ChatActivity mChatActivity;

    public ChatAdapter(List<Messages>  messageList, ChatActivity chatActivity, RecyclerView recyclerView) {
        mMessageList = messageList;
        mContext = chatActivity;
        mRecyclerView = recyclerView;
        mediaPlayerList = new ArrayList<>();
        messageIdList = new ArrayList<>();
        mChatActivity = chatActivity;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessagesViewHolder holder, final int position) {

        holder.holderPosition = position;
        if (TextUtils.equals(mMessageList.get(position).senderId,
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.mMessagesRightLayout.setVisibility(View.VISIBLE);
            holder.mMessagesLeftLayout.setVisibility(View.GONE);
            holder.audioPlayerRight.setVisibility(View.GONE);
            holder.mMessagesMsgWrapperRight.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_right));
            if(mContext.getResources().getString(R.string.txt_read_staus_sent).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                holder.rightMessageStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.msgsent));
                //holder.rightMessageStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_black_24dp));
            }else if(mContext.getResources().getString(R.string.txt_read_staus_received).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                holder.rightMessageStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.msgread));
            }if(mContext.getResources().getString(R.string.txt_read_staus_sending).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                mChatActivity.updateMessageFirebase("readStatus",messageIdList.get(position),mContext.getResources().getString(R.string.txt_read_staus_sent));
            }

            holder.mMessagesRightDateTxt.setText(getFormattedDate(mMessageList.get(position).getCreatedDate()));
            if (mMessageList.get(position).getText() != null && !mMessageList.get(position).getText().isEmpty()) {
                holder.mMessagesMsgWrapperRight.setVisibility(View.VISIBLE);
                holder.mMessagesImageLayoutRight.setVisibility(View.GONE);
                holder.audioPlayerRight.setVisibility(View.GONE);
                //holder.mMessagesMsgWrapperRight.setBackgroundResource(R.drawable.blue_right);
                holder.mMessagesRightMessageTxt.setTextColor(Color.WHITE);
                Messages chat = null;
                if(mContext.getResources().getString(R.string.txt_read_staus_sending).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                    mChatActivity.updateMessageFirebase("readStatus",messageIdList.get(position),mContext.getResources().getString(R.string.txt_read_staus_sent));
                }
                final String message = mMessageList.get(position).getText();
                holder.mMessagesRightMessageTxt.setText(message);
                if(URLUtil.isValidUrl(message)){
                    holder.mMessagesRightMessageTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(message));
                            mContext.startActivity(i);
                        }
                    });

                }
            } else if (mMessageList.get(position).getImageUrlA() != null && !mMessageList.get(position).getImageUrlA().isEmpty()) {
                holder.mMessagesImageLayoutRight.setVisibility(View.VISIBLE);
                holder.mMessagesMsgWrapperRight.setVisibility(View.GONE);
                holder.audioPlayerRight.setVisibility(View.GONE);
                holder.mProgressBarImageRight.setVisibility(View.VISIBLE);
                if(mContext.getResources().getString(R.string.txt_read_staus_sending).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                    mChatActivity.updateMessageFirebase("readStatus",messageIdList.get(position),mContext.getResources().getString(R.string.txt_read_staus_sent));
                }
                if(!mMessageList.get(position).getImageUrlA().equalsIgnoreCase("NOT SET")) {
                    Glide.with(mContext)
                            .load(Uri.parse(mMessageList.get(position).getImageUrlA()))
                            .override(300, 300)
                            .listener(new RequestListener<Uri, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    holder.mProgressBarImageRight.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    holder.mProgressBarImageRight.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(holder.mIssueImageRight);
                    holder.mIssueImageRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FullScreenImage fullScreenImage = new FullScreenImage();
                            Bundle bundle = new Bundle();
                            bundle.putString(mContext.getString(R.string.dialog_title), "Image from " + mMessageList.get(holder.holderPosition).getSenderName());
                            bundle.putString(mContext.getString(R.string.dialog_img_url), mMessageList.get(holder.holderPosition).getImageUrlA());
                            fullScreenImage.setArguments(bundle);
                            fullScreenImage.show(((ChatActivity) mContext).getFragmentManager(), "");
                        }
                    });
                }
            }else if (mMessageList.get(holder.holderPosition).getAudioURL_a() != null && !mMessageList.get(holder.holderPosition).getAudioURL_a().isEmpty()) {
                holder.mMessagesImageLayoutRight.setVisibility(View.GONE);
                holder.mMessagesMsgWrapperRight.setVisibility(View.GONE);
                holder.audioPlayerRight.setVisibility(View.VISIBLE);
                if(mContext.getResources().getString(R.string.txt_read_staus_sending).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                    mChatActivity.updateMessageFirebase("readStatus",messageIdList.get(position),mContext.getResources().getString(R.string.txt_read_staus_sent));
                }
                if (mMessageList.get(holder.holderPosition).getLocalAudioUrl() == null) {
                    mediaPlayerList.set(position,holder.audioPlayerRight.setDownloadUrl(mMessageList.get(holder.holderPosition).getAudioURL_a(), position, this));
                    //holder.rightMessageStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_access_time_black_24dp));
                } else {
                    holder.audioPlayerRight.dismissProgressBar();
                    mediaPlayerList.set(position,holder.audioPlayerRight.setMusicFile(mMessageList.get(holder.holderPosition).getLocalAudioUrl(),position,this));
                    if(mContext.getResources().getString(R.string.txt_read_staus_sent).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                        holder.rightMessageStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.msgsent));
                    }else if(mContext.getResources().getString(R.string.txt_read_staus_received).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                        holder.rightMessageStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.msgread));
                    }
                }

            }else if(mMessageList.get(holder.holderPosition).getPdfUrlA() != null && !mMessageList.get(holder.holderPosition).getPdfUrlA().isEmpty()){
                holder.mMessagesMsgWrapperRight.setVisibility(View.VISIBLE);
                holder.mMessagesMsgWrapperRight.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_insert_drive_file_black_60dp));
                holder.mMessagesImageLayoutRight.setVisibility(View.GONE);
                holder.audioPlayerRight.setVisibility(View.GONE);
                //holder.mMessagesMsgWrapperRight.setBackgroundResource(R.drawable.blue_right);
                holder.mMessagesRightMessageTxt.setTextColor(Color.WHITE);
                Messages chat = null;
                if(mContext.getResources().getString(R.string.txt_read_staus_sending).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                    mChatActivity.updateMessageFirebase("readStatus",messageIdList.get(position),mContext.getResources().getString(R.string.txt_read_staus_sent));
                }
                final String message = mMessageList.get(position).getText();
                holder.mMessagesRightMessageTxt.setText("PDF");
                holder.mMessagesMsgWrapperRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChatActivity.setPDFIntent(mMessageList.get(position).getPdfUrlA(),position);

                    }
                });
            }

        } else {
            holder.mMessagesReciverNameTxt.setVisibility(View.GONE);
            holder.mMessagesRightLayout.setVisibility(View.GONE);
            holder.mMessagesLeftLayout.setVisibility(View.VISIBLE);
            holder.leftMessageStatus.setVisibility(View.GONE);
            //holder.audioPlayerRight.clearView();
            mChatActivity.updateMessageFirebase("readStatus",messageIdList.get(position),
                    mContext.getResources().getString(R.string.txt_read_staus_received));
            holder.mMessagesLeftDateTxt.setText(getFormattedDate(mMessageList.get(position).getCreatedDate()));
            if (mMessageList.get(position).getText() != null && !mMessageList.get(position).getText().isEmpty()) {
                holder.nMessagesMsgWrapperLeft.setVisibility(View.VISIBLE);
                holder.mMessagesImageLayoutLeft.setVisibility(View.GONE);
                holder.audioPlayerLeft.setVisibility(View.GONE);
                //holder.nMessagesMsgWrapperLeft.setBackgroundResource(R.drawable.blue);
                holder.mMessagesLeftMessageTxt.setTextColor(Color.WHITE);
                final String message = mMessageList.get(position).getText();
                holder.mMessagesLeftMessageTxt.setText(message);
                if(URLUtil.isValidUrl(message)){
                    holder.mMessagesLeftMessageTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(message));
                            mContext.startActivity(i);
                        }
                    });

                }
            } else if (mMessageList.get(position).getImageUrlA() != null && !mMessageList.get(position).getImageUrlA().isEmpty()) {
                holder.audioPlayerLeft.setVisibility(View.GONE);
                holder.nMessagesMsgWrapperLeft.setVisibility(View.GONE);
                holder.mMessagesImageLayoutLeft.setVisibility(View.VISIBLE);
                holder.mProgressBarImageLeft.setVisibility(View.VISIBLE);
                if(!mMessageList.get(position).getImageUrlA().equalsIgnoreCase("NOT SET")) {
                    Glide.with(mContext)
                            .load(Uri.parse(mMessageList.get(position).getImageUrlA()))
                            .override(300, 300)
                            .listener(new RequestListener<Uri, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    e.printStackTrace();
                                    holder.mProgressBarImageLeft.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    holder.mProgressBarImageLeft.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(holder.mIssueImageLeft);
                    holder.mIssueImageLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FullScreenImage fullScreenImage = new FullScreenImage();
                            Bundle bundle = new Bundle();
                            bundle.putString(mContext.getString(R.string.dialog_title), "Image from " + mMessageList.get(holder.holderPosition).getSenderName());
                            bundle.putString(mContext.getString(R.string.dialog_img_url), mMessageList.get(holder.holderPosition).getImageUrlA());
                            fullScreenImage.setArguments(bundle);
                            fullScreenImage.show(((ChatActivity) mContext).getFragmentManager(), "");
                        }
                    });
                }
            }else if (mMessageList.get(holder.holderPosition).getAudioURL_a() != null && !mMessageList.get(holder.holderPosition).getAudioURL_a().isEmpty()) {
                holder.nMessagesMsgWrapperLeft.setVisibility(View.GONE);
                holder.mMessagesImageLayoutLeft.setVisibility(View.GONE);
                holder.audioPlayerLeft.setVisibility(View.VISIBLE);
                if(mMessageList.get(holder.holderPosition).getLocalAudioUrl()==null) {
                    mediaPlayerList.set(position,holder.audioPlayerRight.setDownloadUrl(mMessageList.get(holder.holderPosition).getAudioURL_a(), position, this));
                    mediaPlayerList.set(position,holder.audioPlayerLeft.setDownloadUrl(mMessageList.get(holder.holderPosition).getAudioURL_a(),position,this));
                    holder.leftMessageStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_access_time_black_24dp));
                }else{
                    holder.audioPlayerLeft.dismissProgressBar();
                    mediaPlayerList.set(position,holder.audioPlayerLeft.setMusicFile(mMessageList.get(holder.holderPosition).getLocalAudioUrl(),position,this));
                    holder.leftMessageStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_check_black_24dp));
                }

            }else if(mMessageList.get(holder.holderPosition).getPdfUrlA() != null && !mMessageList.get(holder.holderPosition).getPdfUrlA().isEmpty()){
                holder.nMessagesMsgWrapperLeft.setVisibility(View.VISIBLE);
                holder.nMessagesMsgWrapperLeft.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_insert_drive_file_black_60dp));
                holder.mMessagesImageLayoutLeft.setVisibility(View.GONE);
                holder.audioPlayerLeft.setVisibility(View.GONE);
                //holder.mMessagesMsgWrapperRight.setBackgroundResource(R.drawable.blue_right);
                holder.mMessagesLeftMessageTxt.setTextColor(Color.WHITE);
                Messages chat = null;
                if(mContext.getResources().getString(R.string.txt_read_staus_sending).equalsIgnoreCase(mMessageList.get(position).getReadStatus())){
                    mChatActivity.updateMessageFirebase("readStatus",messageIdList.get(position),mContext.getResources().getString(R.string.txt_read_staus_sent));
                }
                final String message = mMessageList.get(position).getText();
                holder.mMessagesLeftMessageTxt.setText("PDF");
            }
        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }




    @Override
    public void executeOnPlay(int position) {
        if(selectedPosition!=-1 && selectedPosition != position){
            mediaPlayerList.get(selectedPosition).stopMusicPlayer();
        }
        selectedPosition = position;

    }

    @Override
    public void executeOnStop(int position, AudioPlayerWidget audioPlayerWidget) {
        mediaPlayerList.set(position,audioPlayerWidget);
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {

        TextView mMessagesLeftMessageTxt, mMessagesRightMessageTxt, mMessagesLeftDateTxt, mMessagesRightDateTxt, mGroupDateTxt, mMessagesReciverNameTxt,mMessagesSenderNameTxt;
        LinearLayout mMessagesLeftLayout, mMessagesRightLayout, nMessagesMsgWrapperLeft, mMessagesMsgWrapperRight;
        ImageView mIssueImageLeft, mIssueImageRight;
        ProgressBar mProgressBarImageLeft, mProgressBarImageRight;
        RelativeLayout mMessagesImageLayoutLeft, mMessagesImageLayoutRight;
        AudioPlayerWidget audioPlayerLeft, audioPlayerRight;
        int holderPosition;
        ImageView rightMessageStatus,leftMessageStatus;

        public MessagesViewHolder(View itemView) {
            super(itemView);
            mMessagesLeftLayout         = itemView.findViewById(R.id.chat_left_msg_layout);
            mMessagesLeftMessageTxt     = itemView.findViewById(R.id.chat_left_msg_text_view);
            mMessagesReciverNameTxt     = itemView.findViewById(R.id.chat_left_reciever_name_text_view);
            mMessagesSenderNameTxt      = itemView.findViewById(R.id.chat_right_sender_name_text_view);
            mMessagesLeftDateTxt        = itemView.findViewById(R.id.txt_time_left);
            mMessagesRightLayout        = itemView.findViewById(R.id.chat_right_msg_layout);
            mMessagesRightMessageTxt    = itemView.findViewById(R.id.chat_right_msg_text_view);
            mMessagesRightDateTxt       = itemView.findViewById(R.id.txt_time_right);
            mMessagesMsgWrapperRight    = itemView.findViewById(R.id.chat_txt_layout_right);
            nMessagesMsgWrapperLeft     = itemView.findViewById(R.id.chat_txt_layout_left);
            mIssueImageLeft         = itemView.findViewById(R.id.chat_msg_image_left);
            mIssueImageRight        = itemView.findViewById(R.id.chat_msg_image_right);
            mProgressBarImageLeft   = itemView.findViewById(R.id.progress_left);
            mProgressBarImageRight  = itemView.findViewById(R.id.progress_right);
            mMessagesImageLayoutLeft    = itemView.findViewById(R.id.chat_image_layout_left);
            mMessagesImageLayoutRight   = itemView.findViewById(R.id.chat_image_layout_right);
            mGroupDateTxt           = itemView.findViewById(R.id.txt_group_separtor_date);
            audioPlayerLeft         = itemView.findViewById(R.id.audio_left);
            audioPlayerRight        = itemView.findViewById(R.id.audio_right);
            rightMessageStatus      = itemView.findViewById(R.id.image_read_status_right);
            leftMessageStatus       = itemView.findViewById(R.id.image_read_status_left);


            /*mProgressBarImageLeft.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
            mProgressBarImageRight.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);*/
        }
    }

    public void add(Messages chat,String messageKey) {
        mMessageList.add(chat);
        mediaPlayerList.add(null);
        messageIdList.add(messageKey);
        notifyItemInserted(mMessageList.size() - 1);
        mRecyclerView.scrollToPosition(getItemCount() - 1);
    }

    public void update(Messages chat) {
        int inextUPDATED = Collections.binarySearch(mMessageList, chat);
        mediaPlayerList.add(null);
        if (inextUPDATED >= 0) {
            mMessageList.set(inextUPDATED,chat);
            notifyItemChanged(inextUPDATED);
        }
    }

    public void addItem(Messages chatMessage) {
        mMessageList.add(chatMessage);
        notifyItemInserted(mMessageList.size() - 1);
        mRecyclerView.scrollToPosition(getItemCount() - 1);
        notifyDataSetChanged();
    }

    public void updateItem(Messages chatMessage) {
        int inextUPDATED = Collections.binarySearch(mMessageList, chatMessage);
        if (inextUPDATED >= 0) {
            ((Messages) mMessageList.get(inextUPDATED)).setImageUrlA(chatMessage.getImageUrlA());
            notifyItemChanged(inextUPDATED);
        }

    }

    public void addItem(List<Messages> chatMessageList) {
        mMessageList.addAll(chatMessageList);
        mRecyclerView.scrollToPosition(getItemCount() - 1);
        notifyDataSetChanged();
    }

    public String getFormattedDate(String dateString1, String dateString2, int pos) {
        String returnDateString = null;

        try {
            long timestamp = Long.parseLong(dateString1);
            long timestamp2 = Long.parseLong(dateString2);

            Calendar cal1 = Calendar.getInstance();
            cal1.setTimeInMillis(timestamp);
            Date date = cal1.getTime();
            cal1.set(Calendar.HOUR_OF_DAY, 0);
            cal1.set(Calendar.MINUTE, 0);
            cal1.set(Calendar.SECOND, 0);
            cal1.set(Calendar.MILLISECOND, 0);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTimeInMillis(timestamp2);
            cal2.set(Calendar.HOUR_OF_DAY, 0);
            cal2.set(Calendar.MINUTE, 0);
            cal2.set(Calendar.SECOND, 0);
            cal2.set(Calendar.MILLISECOND, 0);

            if(!cal2.getTime().equals(cal1.getTime())){
                SimpleDateFormat sourceSimpleDateFormat = new SimpleDateFormat("EEE, d MMM");
                returnDateString = sourceSimpleDateFormat.format(date);
            }

            if(pos==0){
                SimpleDateFormat sourceSimpleDateFormat = new SimpleDateFormat("EEE, d MMM");
                returnDateString = sourceSimpleDateFormat.format(date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnDateString;
    }

    public String getFormattedDate(String dateString) {
        String returnDateString = "";
        System.out.println("--->" + dateString);
        try {
            long timestamp = Long.parseLong(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            Date date = cal.getTime();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
            currentCalendar.set(Calendar.MINUTE, 0);
            currentCalendar.set(Calendar.SECOND, 0);
            currentCalendar.set(Calendar.MILLISECOND, 0);

            SimpleDateFormat DestinationSimpleDateFormat = new SimpleDateFormat("hh:mm a");
            returnDateString = DestinationSimpleDateFormat.format(date);
            /*if (cal.getTime().equals(currentCalendar.getTime())) {
                SimpleDateFormat DestinationSimpleDateFormat = new SimpleDateFormat("hh:mm a");
                returnDateString = DestinationSimpleDateFormat.format(date);
            }else {
                SimpleDateFormat sourceSimpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy hh:mm a");
                returnDateString = sourceSimpleDateFormat.format(date);
            }*/

            //date = sourceSimpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnDateString;

    }

    /**
     * getting Today yesterday and date format as per chat required
     * @param time
     * @return
     */
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("MMM dd,yyyy", cal).toString();
        Log.i("Timestamp to date",date+"");
        if(date.equals(getTodayDate())){
            Log.i("tag","Today");
            return "Today";
        }else if(date.equals(getYesterdayDate())){
            Log.i("tag","Yesterday");
            return "Yesterday";
        }else{
            Log.i("tag",date);
            return date;
        }
    }

    /**
     *
     * @return Yesterday date  in format "dd-MM-yyyy
     */
    private String getYesterdayDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String date = DateFormat.format("MMM dd,yyyy", cal).toString();
        //Log.i("yesterday date",date+"");
        return date;
    }

    /**
     *
     * @return Today date  in format "dd-MM-yyyy
     */
    private String getTodayDate(){
        Long timestamp= System.currentTimeMillis();
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("MMM dd,yyyy", cal).toString();
        //Log.i("Timestamp to date",date+"");
        return date;
    }
    
}
