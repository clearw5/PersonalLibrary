package com.stardust.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Stardust on 2016/10/21.
 */

public class AlarmMediaPlayer {

    public interface OnPauseListener {
        void onPause(AlarmMediaPlayer player);
    }

    private static final String TAG = "AlarmMediaPlayer";
    private MediaPlayer mMediaPlayer;
    private Timer mTimer;
    private int mPlayDuration;
    private boolean mIsStop = false;
    private final Object mIsStopLock = new Object();
    private boolean mIsPlaying = false;
    private final Object mIsPlayingLock = new Object();
    private float mVolume = 1.0f;
    private OnPauseListener mOnPauseListener;

    public AlarmMediaPlayer(MediaPlayer mediaPlayer, int playDuration) {
        mMediaPlayer = mediaPlayer;
        mediaPlayer.setLooping(true);
        mPlayDuration = playDuration;
    }

    public AlarmMediaPlayer(Context context, int res, int playDuration) {
        this(MediaPlayer.create(context, res), playDuration);
    }

    public void play() {
        if (mTimer == null) {
            throw new IllegalStateException("cannot call play until start");
        }
        synchronized (mIsPlayingLock) {
            if (mIsPlaying)
                return;
            mIsPlaying = true;
        }
        mMediaPlayer.setVolume(mVolume, mVolume);
        Log.i(TAG, "Media Player playing at volume: " + mVolume);
        mTimer.schedule(new TimerTask() {
            private long millis = System.currentTimeMillis();

            @Override
            public void run() {
                if (System.currentTimeMillis() - millis >= mPlayDuration) {
                    pauseMediaPlayer();
                    cancel();
                }
                synchronized (mIsStopLock) {
                    if (mIsStop) {
                        pauseMediaPlayer();
                        cancel();
                    }
                }
            }
        }, 0, 200);
    }

    private void pauseMediaPlayer() {
        try {
            mMediaPlayer.setVolume(0, 0);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        synchronized (mIsPlayingLock) {
            mIsPlaying = false;
        }
        if (mOnPauseListener != null)
            mOnPauseListener.onPause(this);
        Log.i(TAG, "Media Player pause");
    }

    public void stop() {
        if (mTimer == null)
            throw new IllegalStateException("cannot stop until starting it");
        Log.i(TAG, "Media Player stop");
        mTimer.cancel();
        mTimer = null;
        pauseMediaPlayer();
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
        mIsStop = true;
    }

    public void start() {
        if (mTimer != null)
            throw new IllegalStateException("cannot start twice without stopping");
        Log.i(TAG, "Media Player start");
        mTimer = new Timer();
        if (!mMediaPlayer.isPlaying())
            mMediaPlayer.start();
        mMediaPlayer.setVolume(0, 0);
        mIsStop = false;
    }

    public void setOnPauseListener(OnPauseListener listener) {
        mOnPauseListener = listener;
    }

    public boolean isStop() {
        return mIsStop;
    }

    public void release() {
        if (mTimer != null) {
            stop();
        }
        mMediaPlayer.release();
    }

    public int getPlayDuration() {
        return mPlayDuration;
    }

    public void setPlayDuration(int playDuration) {
        mPlayDuration = playDuration;
    }

    public void pauseImmediately() {
        Log.i(TAG, "pauseImmediately");
        pauseMediaPlayer();
    }

    public void setVolume(int volume) {
        Log.i(TAG, "setVolume:" + volume);
        mVolume = volume;
    }

    public float getVolume() {
        return mVolume;
    }
}
