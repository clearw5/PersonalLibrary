package com.stardust.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Stardust on 2016/8/9.
 */
public abstract class ScaleableView extends View implements View.OnTouchListener, Scaleable {

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1f;
    private float mPivotX, mPivotY;

    public ScaleableView(Context context) {
        super(context);
        init();
    }


    public ScaleableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScaleableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ScaleableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    protected void init() {
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener(this));
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        if (!mScaleDetector.isInProgress()) {
            float[] originalXY = invScale(event.getX(), event.getY());
            return onTouch(v, event, originalXY[0], originalXY[1]);
        }
        return true;
    }

    private float[] invScale(float x, float y) {
        x = mPivotX + (x - mPivotX) / mScaleFactor;
        y = mPivotY + (y - mPivotY) / mScaleFactor;
        return new float[]{x, y};
    }

    public abstract boolean onTouch(View view, MotionEvent event, float x, float y);

    protected void dispatchDraw(Canvas canvas) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.scale(mScaleFactor, mScaleFactor, mPivotX, mPivotY);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public void scale(float scaleFactor, float pivotX, float pivotY) {
        mScaleFactor = scaleFactor;
        mPivotX = pivotX;
        mPivotY = pivotY;
        this.invalidate();
    }

    public void restore() {
        mScaleFactor = 1;
        this.invalidate();
    }

}