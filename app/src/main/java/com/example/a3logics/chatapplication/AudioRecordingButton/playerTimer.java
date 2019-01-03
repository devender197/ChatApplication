package com.example.a3logics.chatapplication.AudioRecordingButton;

import android.media.MediaPlayer;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class playerTimer {
    private MediaPlayer mMedia;
    private PlayerTimerInterface mPlayerTimerInterface;
    private String currentDuration;
    private double finalTime;
    private double startTime;
    private Handler myHandler = new Handler();
    private Runnable UpdateSongTime;

    public playerTimer(MediaPlayer media, final PlayerTimerInterface playerTimerInterface){
        mMedia = media;
        mPlayerTimerInterface = playerTimerInterface;
        UpdateSongTime = new Runnable() {
            public void run() {
                if(mMedia.isPlaying()) {
                    startTime = mMedia.getCurrentPosition();
                    currentDuration = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)));
                    mPlayerTimerInterface.updateUI(currentDuration);
                    myHandler.postDelayed(this, 100);
                }else{
                    myHandler.removeCallbacks(this);
                }
            }
        };

    }





    public void attachHandler(){
        finalTime = mMedia.getDuration();
        startTime = mMedia.getCurrentPosition();
        myHandler.postDelayed(UpdateSongTime,100);
    }





}
