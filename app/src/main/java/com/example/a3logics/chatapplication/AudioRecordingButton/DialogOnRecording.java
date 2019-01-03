package com.example.a3logics.chatapplication.AudioRecordingButton;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.a3logics.chatapplication.R;


public class DialogOnRecording extends DialogFragment {
    private TextView mTimerText;
    private int mScreenMode;
    private int LONGPRESS_MODE = 1;
    private int SINGLEPRESS_MODE = 2;
    private ImageView play,cancel,send;
    private Button recordButton;
    private ImageView imageView;
    private boolean isRecording = true;
    private MediaPlayInterface mMediaPlayInterface;
    private LinearLayout ll ;

    public DialogOnRecording(MediaPlayInterface mediaPlayInterface){
        mMediaPlayInterface = mediaPlayInterface;
    }

    public ImageView getPlay() {
        return play;
    }

    public void setPlay(ImageView play) {
        this.play = play;
    }

    public ImageView getCancel() {
        return cancel;
    }

    public void setCancel(ImageView cancel) {
        this.cancel = cancel;
    }

    public ImageView getSend() {
        return send;
    }

    public void setSend(ImageView send) {
        this.send = send;
    }

    public Button getRecordButton() {
        return recordButton;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setRecordButton(Button recordButton) {
        this.recordButton = recordButton;
    }

    public TextView getmTimerText() {
        return mTimerText;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        setCancelable(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            mScreenMode = getArguments().getInt("screen_mode");
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mMediaPlayInterface.stop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        ll = new LinearLayout(getActivity());
        ll.setGravity(Gravity.CENTER);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setPadding(10,20,10,10);
        //ll.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        ll.setBackground(getActivity().getResources().getDrawable(R.drawable.ract_bg));
        imageView = new ImageView(getActivity());
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.ic_mic_black_24dp).into(imageViewTarget);

        if(mScreenMode == LONGPRESS_MODE) {
       //imageView = new ImageView(getActivity());

       imageView.setImageDrawable(getActivity().getResources().
               getDrawable(R.drawable.circular_recording));

            ll.addView(imageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));

            mTimerText = new TextView(getActivity());
            ll.addView(mTimerText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            mTimerText.setTextColor(getActivity().getResources().getColor(R.color.textBlueColor));

        }else if(mScreenMode == SINGLEPRESS_MODE){
          /*
          //  imageView = new ImageView(getActivity());
            *//*imageView.setImageDrawable(getActivity().getResources().
                    getDrawable(R.drawable.circular_recording));
            *//*
            ll.addView(imageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));

            mTimerText = new TextView(getActivity());
            ll.addView(mTimerText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            mTimerText.setTextColor(getActivity().getResources().getColor(R.color.textBlueColor));

            recordButton = new Button(getActivity());
            recordButton.setText("STOP");
            recordButton.setBackground(getResources().getDrawable(R.drawable.stop_icon_off));
            //recordButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            recordButton.setTextColor(Color.WHITE);
            recordButton.setBackgroundColor(getActivity().getResources().getColor(R.color.textBlueColor));
            recordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isRecording) {
                        isRecording = false;
                        recordButton.setText("RECORD");
                        //recordButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                        //recordButton.setBackground(getResources().getDrawable(R.drawable.stop_icon_on));
                        play.setVisibility(View.VISIBLE);
                        send.setVisibility(View.VISIBLE);
                        mMediaPlayInterface.stop();
                    }else{
                        isRecording = true;
                        recordButton.setText("STOP");
                        //recordButton.setBackground(getResources().getDrawable(R.drawable.stop_icon_off));
                        //recordButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                        play.setVisibility(View.INVISIBLE);
                        send.setVisibility(View.INVISIBLE);
                        mMediaPlayInterface.start();
                    }
                }
            });

            ll.addView(recordButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));

            LinearLayout nestedLinearLayout = new LinearLayout(getActivity());
            nestedLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            nestedLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            play = new Button(getActivity());
            play.setTextColor(Color.WHITE);
            play.setBackgroundColor(getActivity().getResources().getColor(R.color.textBlueColor));
            //play.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            play.setText("PLAY");
            //play.setBackground(getResources().getDrawable(R.drawable.audio_play));
            nestedLinearLayout.addView(play, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.circular_recording));
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(play.getText().toString().equalsIgnoreCase("PLAY")) {
                        play.setText("PAUSE");
                       // play.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                        //play.setBackground(getResources().getDrawable(R.drawable.audio_play_icon_on));
                        imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.circular_playing));

                    }else{
                        play.setText("PLAY");
                       // play.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                       // play.setBackground(getResources().getDrawable(R.drawable.audio_play));
                        imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.circular_recording));

                    }
                    mMediaPlayInterface.play();
                }
            });

            cancel = new Button(getActivity());
            //cancel.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            cancel.setTextColor(Color.WHITE);
            cancel.setBackgroundColor(getActivity().getResources().getColor(R.color.textBlueColor));
            cancel.setText("CANCEL");
            nestedLinearLayout.addView(cancel, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    mMediaPlayInterface.cancel();
                }
            });

            send = new Button(getActivity());
            //send.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            send.setText("SEND");
            send.setTextColor(Color.WHITE);
            send.setBackgroundColor(getActivity().getResources().getColor(R.color.textBlueColor));
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMediaPlayInterface.send();
                }
            });
            nestedLinearLayout.addView(send, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            ll.addView(nestedLinearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            play.setVisibility(View.INVISIBLE);
            send.setVisibility(View.INVISIBLE);*/
            View rootView = inflater.inflate(R.layout.record_dialog, container, false);
            initview(rootView);
            return rootView;
        }

        return ll;

    }

    void initview(View view){
        send            = view.findViewById(R.id.send);
        recordButton    = view.findViewById(R.id.record);
        play            = view.findViewById(R.id.play_pause_button);
        cancel          = view.findViewById(R.id.cancel);
        imageView       = view.findViewById(R.id.recording_icon);
        mTimerText      = view.findViewById(R.id.timer_text);
        recordButton.setText("STOP");
        play.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaPlayInterface.send();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mMediaPlayInterface.cancel();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(play.getDrawable().getConstantState().equals(getActivity().getResources().getDrawable(R.drawable.r3).getConstantState())) {
                    play.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pause_recording_dialog));
                    // play.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                    //play.setBackground(getResources().getDrawable(R.drawable.audio_play_icon_on));
                    //imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));

                }else{
                    play.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.r3));
                    // play.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                    // play.setBackground(getResources().getDrawable(R.drawable.audio_play));
                    //imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.r1));

                }
                mMediaPlayInterface.play();
            }
        });
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording) {
                    isRecording = false;
                    recordButton.setText("RECORD");
                    //recordButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                    //recordButton.setBackground(getResources().getDrawable(R.drawable.stop_icon_on));
                    play.setVisibility(View.VISIBLE);
                    send.setVisibility(View.VISIBLE);
                    mMediaPlayInterface.stop();
                }else{
                    isRecording = true;
                    recordButton.setText("STOP");
                    //recordButton.setBackground(getResources().getDrawable(R.drawable.stop_icon_off));
                    //recordButton.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                    play.setVisibility(View.INVISIBLE);
                    send.setVisibility(View.INVISIBLE);
                    mMediaPlayInterface.start();
                }
            }
        });
    }

    public void setBackGroundColor(int color){
        ll.setBackgroundColor(color);
    }

    public void updateTimeToTextView(long min,long sec){

        if (null != mTimerText) {
            mTimerText.setText(splitToComponentTimes(min, sec));
        }
    }

    public void updateTimeToTextView(String time){
        if (null != mTimerText) {
            mTimerText.setText(time);
        }
    }

    public static String splitToComponentTimes(long min , long secValue)
    {
        int hours = (int) secValue / 3600;
        int remainder = (int) secValue - hours * 3600;
        int mins = (int)secValue / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;
        String returnTime = String.format("%02d:%02d", min,secValue);
        return returnTime;
    }
}
