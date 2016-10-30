package com.stardust.widgets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 一个还没有完成的悬浮窗类，主要用于悬浮小球这类，而不是那种需要权限的全局悬浮窗
 */
// TODO class
public class FloatingWindow extends RelativeLayout {

    private FloatingViewOnTouchListener mFloatingViewOnTouchListener;
    private GestureDetector mGestureDetector;
    private View backgroundView;
    private View floatingView;
    private int floatingViewState;

    public static final int STATE_CLICKED = 1;
    public static final int STATE_MOVING = 2;
    public static final int STATE_DISPLAYING = 3;
    public static final int STATE_HIDED = 0;

    public FloatingWindow(Context context) {
        super(context);
    }

    public FloatingWindow(Context context, View backgroundView,
                          View floatingView) {
        super(context);
        this.backgroundView = backgroundView;
        this.floatingView = floatingView;
        initializeListeners();
    }

    private void initializeListeners() {
        floatingView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });

    }

    private class FloatingViewOnTouchListener implements
            OnGestureListener {

        // TODO remove it
        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        @Override
        public boolean onDown(MotionEvent e) {
            floatingView.setX(e.getX());
            floatingView.setY(e.getY());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

    }
}
