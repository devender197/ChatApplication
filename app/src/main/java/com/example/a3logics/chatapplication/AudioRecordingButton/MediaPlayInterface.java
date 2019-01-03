package com.example.a3logics.chatapplication.AudioRecordingButton;

import java.io.Serializable;

public interface MediaPlayInterface extends Serializable {
    void play();
    void send();
    void cancel();
    void start();
    void stop();

}
