package com.stardust.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Stardust on 2016/8/14.
 */
public class CheckButton extends Button implements View.OnTouchListener {

    private boolean mIsChecked = false;
    private int mCheckedColor = Color.GREEN;
    private boolean mIsTextCheckedColor = false;
    private int mUncheckedColor;
    private OnTouchListener mOnTouchListener;

    public CheckButton(Context context) {
        super(context);
        init();
    }

    public CheckButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CheckButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void setColor(int colorChecked, int colorUnchecked) {
        setCheckedColor(colorChecked);
        setUncheckedColor(colorUnchecked);
    }

    public void setCheckedColor(int color) {
        if (mCheckedColor == color)
            return;
        mCheckedColor = color;
    }


    public void setUncheckedColor(int color) {
        if (mUncheckedColor == color)
            return;
        mUncheckedColor = color;
    }

    public void setState(boolean isChecked) {
        if (mIsChecked == isChecked) {
            return;
        }
        mIsChecked = isChecked;
        setTextColor(mIsChecked);
        setPressed(true);
    }

    @Override
    public void setOnTouchListener(OnTouchListener listener) {
        mOnTouchListener = listener;
    }

    private void setTextColor(boolean isChecked) {
        if (isChecked == mIsTextCheckedColor) {
            return;
        }
        mIsTextCheckedColor = isChecked;
        if (mIsTextCheckedColor) {
            setTextColor(mCheckedColor);
        } else {
            setTextColor(mUncheckedColor);
        }
    }

    private void init() {
        super.setOnTouchListener(this);
        mUncheckedColor = getCurrentTextColor();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setTextColor(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            setState(!mIsChecked);
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            setTextColor(false);
        }
        if (mOnTouchListener != null)
            return mOnTouchListener.onTouch(v, event);
        return false;
    }

    public void setPressed(boolean b) {
        super.setPressed(mIsChecked);
    }
}
