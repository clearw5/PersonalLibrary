package com.stardust.widgets;

import android.view.ScaleGestureDetector;

/**
 * Created by Stardust on 2016/8/9.
 */
public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    float startingSpan;
    float startFocusX;
    float startFocusY;
    Scaleable mScaleable;
    boolean mRestoreAfterScaleEnd = false;
    float mLastScaleFactor = 1.0f;
    float mCurrentScaleFactor;

    public ScaleListener(Scaleable scaleable) {
        mScaleable = scaleable;
    }

    public ScaleListener(Scaleable scaleable, boolean restoreAfterScaleEnd) {
        mScaleable = scaleable;
        mRestoreAfterScaleEnd = restoreAfterScaleEnd;
    }

    public void setRestoreAfterScaleEnd(boolean restoreAfterScaleEnd) {
        mRestoreAfterScaleEnd = restoreAfterScaleEnd;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        startingSpan = detector.getCurrentSpan();
        startFocusX = detector.getFocusX();
        startFocusY = detector.getFocusY();
        return true;
    }


    public boolean onScale(ScaleGestureDetector detector) {
        mCurrentScaleFactor = detector.getCurrentSpan() / startingSpan;
        mScaleable.scale(mLastScaleFactor * mCurrentScaleFactor, startFocusX, startFocusY);
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
        if (mRestoreAfterScaleEnd)
            mScaleable.restore();
        mLastScaleFactor = mCurrentScaleFactor;
    }
}