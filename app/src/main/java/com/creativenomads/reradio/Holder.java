package com.creativenomads.reradio;

import android.app.Application;

import com.google.android.exoplayer2.ExoPlayer;

public class Holder extends Application {
    private ExoPlayer exoPlayer;

    public ExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    public void setExoPlayer(ExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;
    }
    public void releaseExoPlayer() {
        this.exoPlayer = null;
    }

}
