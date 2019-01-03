package com.example.a3logics.chatapplication.AudioPlayer;

public interface PlayerTimerInterface {
    void updateUI(String currentDuration);
    void updateSeekBar(int position, int maxDuration);
}
