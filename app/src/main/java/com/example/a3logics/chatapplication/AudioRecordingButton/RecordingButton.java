package com.example.a3logics.chatapplication.AudioRecordingButton;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.a3logics.chatapplication.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordingButton extends AppCompatButton implements MediaPlayInterface,PlayerTimerInterface{

    private Context mContext;
    private int LONGPRESS_MODE = 1;
    private int SINGLEPRESS_MODE = 2;
    private boolean isSpeakButtonLongPressed;
    private Handler mHandler;
    private DialogOnRecording mDialogOnRecording;
    final int MSG_START_TIMER = 0;
    final int MSG_STOP_TIMER = 1;
    final int MSG_UPDATE_TIMER = 2;
    StopWatch timer = new StopWatch();
    final int REFRESH_RATE = 100;
    private MediaRecorder mRecorder = null;
    private static String mFileName = null;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.RECORD_AUDIO};
    private ResultResponse mResultResponse;
    MediaPlayer mediaPlayer = null;
    private int mScreenMode;
    private Button play;
    private Button cancel;
    private Button send;
    private Button recordButton;
    private TextView timerTextView;
    private TextView imageView;
    private playerTimer mPlayerTimer;


    public TextView getTimerTextView() {
        return timerTextView;
    }

    public ImageView getImageView(){
        return mDialogOnRecording.getImageView();
    }

    public ImageView getPlay() {
        return mDialogOnRecording.getPlay();
    }

    public ImageView getCancel() {
        return mDialogOnRecording.getCancel();
    }

    public ImageView getSend() {
        return mDialogOnRecording.getSend();
    }

    public Button getRecordButton() {
        return mDialogOnRecording.getRecordButton();
    }

    public RecordingButton(Context context) {
        super(context);
        mContext = context;
        mHandler = createHandler();

    }

    public RecordingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mHandler = createHandler();

    }

    public RecordingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mHandler = createHandler();

    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        OnLongClickListener longClickListener = new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (!hasPermissions(mContext, PERMISSIONS)) {
                    ActivityCompat.requestPermissions((AppCompatActivity) mContext, PERMISSIONS, PERMISSION_ALL);
                } else {
                    isSpeakButtonLongPressed = true;
                    startRecording();
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("screen_mode",LONGPRESS_MODE);
                    mDialogOnRecording = new DialogOnRecording(RecordingButton.this);
                    mDialogOnRecording.setArguments(mBundle);
                    mDialogOnRecording.show(((Activity) mContext).getFragmentManager(), "");
                }
                return true;
            }
        };
        super.setOnLongClickListener(longClickListener);

    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(mContext, PERMISSIONS)) {
                    ActivityCompat.requestPermissions((AppCompatActivity) mContext, PERMISSIONS, PERMISSION_ALL);
                } else {
                    startRecording();
                    Bundle mBundle = new Bundle();
                    mBundle.putInt("screen_mode",SINGLEPRESS_MODE);
                    mDialogOnRecording = new DialogOnRecording(RecordingButton.this);
                    mDialogOnRecording.setArguments(mBundle);
                    mDialogOnRecording.show(((Activity) mContext).getFragmentManager(), "");
                }
            }
        };
        super.setOnClickListener(onClickListener);
    }

    private Handler createHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_START_TIMER:
                        timer.start(); //start timer
                        mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
                        break;

                    case MSG_UPDATE_TIMER:
                        mDialogOnRecording.updateTimeToTextView(timer.getElapsedTimeMin(), timer.getElapsedTimeSecs());
                        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER, REFRESH_RATE); //text view is updated every second,
                        break;
                    //though the timer is still running
                    case MSG_STOP_TIMER:
                        mHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
                        timer.stop();//stop timer
                        //mDialogOnRecording.updateTimeToTextView(timer.getElapsedTimeMin(), timer.getElapsedTimeSecs());
                        break;

                    default:
                        break;
                }
            }
        };
    }


    @Override
    public void setOnTouchListener(OnTouchListener l) {
        OnTouchListener speakTouchListener = new OnTouchListener() {

            @Override
            public boolean onTouch(View pView, MotionEvent pEvent) {
                pView.onTouchEvent(pEvent);
                if (pEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (isSpeakButtonLongPressed) {
                        mHandler.sendEmptyMessage(MSG_STOP_TIMER);
                        isSpeakButtonLongPressed = false;
                        if (mDialogOnRecording != null) {
                            mDialogOnRecording.dismiss();
                            stopRecording();
                        }
                    }
                }
                return true;
            }
        };
        super.setOnTouchListener(speakTouchListener);
    }

    public void startRecording() {
            // The request code used in ActivityCompat.requestPermissions()and returned in the AppCompatActivity's onRequestPermissionsResult()
            try {
                mFileName = getOutputImageUri(mContext).getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mRecorder.setAudioSamplingRate(12000);
            mRecorder.setOutputFile(mFileName);
            mHandler.sendEmptyMessage(MSG_START_TIMER);

            try {
                mRecorder.prepare();
                mRecorder.start();

            } catch (IOException e) {
                e.printStackTrace();
            }catch (IllegalStateException e){
                e.printStackTrace();

            }
    }

    private void stopRecording() {
        if(mRecorder!=null ) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            System.out.println("mFile path on recording" + mFileName);
            mHandler.sendEmptyMessage(MSG_STOP_TIMER);
            //mResultResponse.responseResult(mFileName);
        }
    }

    /**
     * following function is used to check the permission at run time for device above android M.
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    File getOutputImageUri(Context context) throws IOException {
        String mCurrentPhotoPath;

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "SOUND_CLIP_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File image = File.createTempFile(imageFileName,  /* prefix */".mp4", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void setLongClickResultListener(ResultResponse resultResponse) {
        mScreenMode = LONGPRESS_MODE;
        mResultResponse = resultResponse;
        setOnLongClickListener(null);
        setOnTouchListener(null);

    }

    public void setClickResultListener(ResultResponse resultResponse) {
        mScreenMode = SINGLEPRESS_MODE;
        mResultResponse = resultResponse;
        setOnClickListener(null);

    }

    @Override
    public void play() {
        playMusic();
    }

    @Override
    public void send() {
        if(mDialogOnRecording!=null){
            mDialogOnRecording.dismiss();
            mResultResponse.responseResult(mFileName);
        }
    }

    @Override
    public void cancel() {
        if(mDialogOnRecording!=null){
            mDialogOnRecording.dismiss();
            stop();
            if(mFileName!=null){
                File audioFile = new File(mFileName);
                audioFile.delete();
            }
        }
    }

    @Override
    public void start() {
            startRecording();
    }

    @Override
    public void stop() {
            stopRecording();
            stopMusicPlayer();
    }

    private void stopMusicPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            updateUI("00:00");
            mediaPlayer= null;
        }
    }

    private void playMusic(){
        System.out.println("mFile path on playing" + mFileName);
        Uri myUri = null;
        if(mediaPlayer==null ) {
            myUri = Uri.fromFile(new File(mFileName));
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(mContext, myUri);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                        mPlayerTimer.attachHandler();
                    }
                });
                mediaPlayer.prepare();
                getRecordButton().setVisibility(INVISIBLE);
                mPlayerTimer = new playerTimer(mediaPlayer,this);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        getPlay().setImageDrawable(mContext.getDrawable(R.drawable.r3));
                        getImageView().setImageDrawable(mContext.getResources().getDrawable(R.drawable.r1));
                        getRecordButton().setVisibility(VISIBLE);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                getRecordButton().setVisibility(VISIBLE);
            }else{
                mediaPlayer.start();
                mPlayerTimer.attachHandler();
                getRecordButton().setVisibility(INVISIBLE);
            }
        }



    }

    @Override
    public void updateUI(String currentDuration) {
            mDialogOnRecording.updateTimeToTextView(currentDuration);
    }

    @Override
    public void updateSeekBar(int position) {

    }
}
