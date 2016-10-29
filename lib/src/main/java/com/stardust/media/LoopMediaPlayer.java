package com.stardust.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Stardust on 2016/10/20.
 */

public class LoopMediaPlayer {

    private static final String TAG = "LoopMediaPlayer";
    private MediaPlayer mMediaPlayer;
    private Timer mTimer;
    private int mLoopInterval, mPlayDuration;
    private boolean mIsPause = false;

    public LoopMediaPlayer(MediaPlayer mediaPlayer, int loopInterval, int playDuration) {
        mMediaPlayer = mediaPlayer;
        mLoopInterval = loopInterval;
        mPlayDuration = playDuration;
        mediaPlayer.setLooping(true);
    }

    public LoopMediaPlayer(Context context, int res, int loopInterval, int playDuration) {
        this(MediaPlayer.create(context, res), loopInterval, playDuration);
    }

    private static void pause(MediaPlayer mediaPlayer){
        mediaPlayer.setVolume(0, 0);
    }

    private static void resume(MediaPlayer mediaPlayer){
        mediaPlayer.setVolume(1, 1);
    }

    public void prepareLoop(){
        if(mTimer != null)
            throw new IllegalStateException("cannot prepare loop twice without stopping loop.");
        pauseLoop();
        mTimer = new Timer();
        mMediaPlayer.start();
        mMediaPlayer.setVolume(0, 0);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (this){
                    if(mIsPause){
                        return;
                    }
                }
                mMediaPlayer.setVolume(1, 1);
                Log.i(TAG, "Media Player playing");
                new Timer().schedule(new TimerTask() {
                    private long millis = System.currentTimeMillis();
                    @Override
                    public void run() {
                        if(System.currentTimeMillis() - millis >= mPlayDuration){
                            pauseMediaPlayer();
                            cancel();
                        }
                        synchronized (this){
                            if(mIsPause){
                                pauseMediaPlayer();
                                cancel();
                            }
                        }
                    }
                }, 0, 200);


            }
        }, 0, mLoopInterval + mPlayDuration);
    }

    private void pauseMediaPlayer(){
        mMediaPlayer.setVolume(0, 0);
        Log.i(TAG, "Media Player pause");
    }

    public void startLoop() {
        prepareLoop();
        resumeLoop();
    }

    public void stopLoop() {
        if(mTimer == null)
            throw new IllegalStateException("cannot stop loop until starting it");
        mTimer.cancel();
        mTimer = null;
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
    }

    public void pauseLoop(){
        synchronized (this){
            mIsPause = true;
        }
    }

    public void resumeLoop(){
        synchronized (this){
            mIsPause = false;
        }
    }

    public boolean isLooping() {
        return !mIsPause && mTimer != null;
    }

    public boolean isStop(){
        return mTimer == null;
    }

    public boolean isPause(){
        return mIsPause;
    }

    public void release() {
        if(mTimer != null){
            stopLoop();
        }
        mMediaPlayer.release();
    }

    public int getLoopInterval() {
        return mLoopInterval;
    }

    public void setLoopInterval(int loopInterval) {
        mLoopInterval = loopInterval;
    }

    public int getPlayDuration() {
        return mPlayDuration;
    }

    public void setPlayDuration(int playDuration) {
        mPlayDuration = playDuration;
    }
}
