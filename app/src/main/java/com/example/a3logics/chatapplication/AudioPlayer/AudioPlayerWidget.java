package com.example.a3logics.chatapplication.AudioPlayer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.a3logics.chatapplication.Common.Utils;
import com.example.a3logics.chatapplication.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;



public class AudioPlayerWidget extends RelativeLayout implements PlayerTimerInterface {
    private ImageView playButton;
    private Context mContext;
    private SeekBar seekBar;
    private String mFileName = null;
    private playerTimer mPlayerTimer;
    private MediaPlayer mediaPlayer = null;
    private TextView mediaTime;
    private boolean isPlaying;
    private ProgressBar mProgressBar;
    private String downloadUrl;
    private File[] files;
    private FragmentPlayerInterface mFragmentPlayerInterface;
    private int mPosition;


    public AudioPlayerWidget(Context context) {
        super(context);
        mContext = context;
        viewAdd();
    }

    public AudioPlayerWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        viewAdd();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }


    public AudioPlayerWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        viewAdd();
    }

    public playerTimer getmPlayerTimer() {
        return mPlayerTimer;
    }

    public ImageView getPlayButton() {
        return playButton;
    }


    public String getmFileName() {
        return mFileName;
    }


    public SeekBar getSeekBar() {
        return seekBar;
    }

    public TextView getMediaTime() {
        return mediaTime;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }


    public AudioPlayerWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        viewAdd();
    }

    public AudioPlayerWidget setMusicFile(String file, int position, FragmentPlayerInterface fragmentPlayerInterface) {
        mPosition = position;
        mFileName = file;
        mFragmentPlayerInterface = fragmentPlayerInterface;
        mPosition = position;
        //playButton.setEnabled(true);
        setMediaPlayer();
        return this;
    }

    public void setMediaPlayer(AudioPlayerWidget audioPlayerWidget) {
        if (audioPlayerWidget != null) {
            mediaPlayer = audioPlayerWidget.getMediaPlayer();
            playButton = audioPlayerWidget.getPlayButton();
            seekBar = audioPlayerWidget.getSeekBar();
            mediaTime = audioPlayerWidget.getMediaTime();
            mPlayerTimer = audioPlayerWidget.getmPlayerTimer();
            mFileName = audioPlayerWidget.getmFileName();
            int startTime1 = mediaPlayer.getCurrentPosition();
            String currentDuration1 = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) startTime1),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime1) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                            toMinutes((long) startTime1)));
            mediaTime.setText(currentDuration1);
            seekBar.setProgress(startTime1);
            try {
                Uri myUri = Uri.fromFile(new File(mFileName));
                mediaPlayer.reset();
                mediaPlayer.setDataSource(mContext, myUri);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mediaPlayer) {
                        int startTime = mediaPlayer.getDuration();
                        String currentDuration = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime)));
                        mediaTime.setText(currentDuration);
                        seekBar.setMax(mediaPlayer.getDuration());
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                mediaPlayer.seekTo(i);
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });
                        mPlayerTimer.attachHandler();
                    }
                });
                mediaPlayer.prepare();
                mPlayerTimer = new playerTimer(mediaPlayer, this);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        getPlayButton().setImageDrawable(mContext.getResources().
                                getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
                        seekBar.setProgress(0);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private MediaPlayer setMediaPlayer() {
        System.out.println("mFile path on playing" + mFileName);
        Uri myUri = Uri.fromFile(new File(mFileName));
        if (mediaPlayer == null) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(mContext, myUri);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mediaPlayer) {
                        int startTime = mediaPlayer.getDuration();
                        playButton.setEnabled(true);
                        String currentDuration = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime)));
                        mediaTime.setText(currentDuration);
                        seekBar.setMax(mediaPlayer.getDuration());
                        mPlayerTimer.attachHandler();
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                mediaPlayer.seekTo(seekBar.getProgress());
                            }
                        });
                    }
                });
                mediaPlayer.prepare();
                mPlayerTimer = new playerTimer(mediaPlayer, this);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        getPlayButton().setImageDrawable(mContext.getResources().
                                getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
                        seekBar.setProgress(0);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mediaPlayer;
    }

    public void playMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            getPlayButton().setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
            mFragmentPlayerInterface.executeOnStop(mPosition, AudioPlayerWidget.this);
        } else {
            mediaPlayer.start();
            mPlayerTimer.attachHandler();
            getPlayButton().setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));
        }


    }

    public AudioPlayerWidget setDownloadUrl(String url, int position, FragmentPlayerInterface fragmentPlayerInterface) {
        downloadUrl = url;
        mPosition = position;
        mFragmentPlayerInterface = fragmentPlayerInterface;

        if (downloadUrl != null && !downloadUrl.isEmpty() && !downloadUrl.equalsIgnoreCase("NOTSET")) {
            AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
            asyncTaskRunner.execute("");
        }

        return this;
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        boolean isSuccess;

        @Override
        protected String doInBackground(String... params) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(downloadUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                mFileName = Utils.getOutputImageUri(mContext,downloadUrl).getAbsolutePath();
                output = new FileOutputStream(mFileName);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    output.write(data, 0, count);
                    isSuccess = true;
                }
            } catch (Exception e) {
                isSuccess = false;
                return e.toString();

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;

        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
           dismissProgressBar();
            if (isSuccess) {
                setMusicFile(mFileName, mPosition, mFragmentPlayerInterface);
            }
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(VISIBLE);
        }

    }


    public void stopMusicPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            getPlayButton().setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
            mFragmentPlayerInterface.executeOnStop(mPosition, AudioPlayerWidget.this);
            // updateUI("00:00");
        }
    }

    private void viewAdd() {
        setGravity(Gravity.RIGHT);
        setPadding(10, 10, 10, 10);
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setCornerRadius(20);
        border.setStroke(1, 0xFF000000); //black border with full opacity
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(border);
        } else {
            setBackground(border);
        }
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        RelativeLayout.LayoutParams params_progress_play = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params_progress_play.addRule(RelativeLayout.CENTER_IN_PARENT);

        RelativeLayout.LayoutParams params_play_pause_container = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params_play_pause_container.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params_play_pause_container.addRule(RelativeLayout.CENTER_IN_PARENT);

        playButton = new ImageView(mContext);
        playButton.setImageDrawable(mContext.getResources().
                getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
        playButton.setEnabled(false);
        playButton.setVisibility(INVISIBLE);


        RelativeLayout relativeLayout_progress = new RelativeLayout(mContext);
        relativeLayout_progress.setId(215);
        relativeLayout_progress.addView(playButton, params_progress_play);
        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyle);
        mProgressBar.setVisibility(VISIBLE);
        mProgressBar.setIndeterminate(true);
        relativeLayout_progress.addView(mProgressBar, params_progress_play);

        addView(relativeLayout_progress, params_play_pause_container);

        RelativeLayout.LayoutParams params_media_time = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params_media_time.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params_media_time.addRule(RelativeLayout.CENTER_IN_PARENT);
        mediaTime = new TextView(mContext);
        mediaTime.setId(512);
        mediaTime.setText("00:00");
        mediaTime.setTextSize(10);
        mediaTime.setGravity(Gravity.RIGHT);
        addView(mediaTime, params_media_time);

        RelativeLayout.LayoutParams params_seek_bar = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params_seek_bar.addRule(RelativeLayout.RIGHT_OF, relativeLayout_progress.getId());
        params_seek_bar.addRule(RelativeLayout.LEFT_OF, mediaTime.getId());
        params_seek_bar.addRule(RelativeLayout.CENTER_IN_PARENT);

        seekBar = new SeekBar(mContext);
        seekBar.setProgress(0);
        seekBar.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        addView(seekBar, params_seek_bar);

        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFileName != null && setMediaPlayer() != null) {
                    mFragmentPlayerInterface.executeOnPlay(mPosition);
                    playMusic();
                    if (mediaPlayer.isPlaying()) {
                        playButton.setImageDrawable(mContext.getResources().
                                getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));

                    } else {
                        playButton.setImageDrawable(mContext.getResources().
                                getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
                    }
                }

            }
        });

    }

    public void setProgressbar() {
        mProgressBar.setVisibility(VISIBLE);
    }

    public void dismissProgressBar() {
        mProgressBar.setVisibility(INVISIBLE);
        playButton.setVisibility(VISIBLE);
    }
  /*  private void addView() {
        setOrientation(VERTICAL);
        setGravity(Gravity.RIGHT);
        setPadding(10, 10, 10, 10);
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setCornerRadius(20);
        border.setStroke(1, 0xFF000000); //black border with full opacity
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(border);
        } else {
            setBackground(border);
        }
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        LinearLayout nestedLinearLayout = new LinearLayout(mContext);
        nestedLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        nestedLinearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        nestedLinearLayout.setGravity(Gravity.CENTER_VERTICAL);

        playButton = new ImageView(mContext);
        playButton.setImageDrawable(mContext.getResources().
                getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
        playButton.setEnabled(false);
        RelativeLayout relativeLayout_progress = new RelativeLayout(mContext);
        relativeLayout_progress.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        relativeLayout_progress.addView(playButton,params);
        mProgressBar = new ProgressBar(mContext,null,android.R.attr.progressBarStyle);
        mProgressBar.setVisibility(INVISIBLE);
        mProgressBar.setIndeterminate(true);
        relativeLayout_progress.addView(mProgressBar,params);

        nestedLinearLayout.addView(relativeLayout_progress, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFileName != null && setMediaPlayer()!=null) {
                    mFragmentPlayerInterface.executeOnPlay(mPosition);
                    playMusic();
                    if (mediaPlayer.isPlaying()) {
                        playButton.setImageDrawable(mContext.getResources().
                                getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));

                    } else {
                        playButton.setImageDrawable(mContext.getResources().
                                getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
                    }
                }

            }
        });

        seekBar = new SeekBar(mContext);
        *//*seekBar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });*//*
        seekBar.setProgress(0);
        seekBar.getProgressDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        nestedLinearLayout.addView(seekBar, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 0));

        addView(nestedLinearLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0));

        mediaTime = new TextView(mContext);
        mediaTime.setText("00:00");
        mediaTime.setTextSize(10);
        mediaTime.setPadding(0, 0, 30, 0);
        mediaTime.setGravity(Gravity.RIGHT);
        addView(mediaTime, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0));

    }*/

    public void clearView() {
        mediaTime.setText("00:00");
        mediaPlayer = null;
        seekBar.setProgress(0);

    }

    public void play() {
        mediaPlayer.start();
        mPlayerTimer.attachHandler();
        getPlayButton().setImageDrawable(mContext.getResources().
                getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            getPlayButton().setImageDrawable(mContext.getResources().
                    getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
        }
    }

    @Override
    public void updateUI(String currentDuration) {
        mediaTime.setText(currentDuration);
    }

    @Override
    public void updateSeekBar(int position, int max) {
        //seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(position);
    }

    private String getAllFileName(String url) {
        String returnFileUrl = null;
        String fileName = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
        String path = mContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
        File directory = new File(path);
        files = directory.listFiles();
        for (File file : files) {
            if (file.getName().equalsIgnoreCase(fileName)) {
                returnFileUrl = file.getAbsolutePath();
                break;
            }
        }
        return returnFileUrl;
    }

}
