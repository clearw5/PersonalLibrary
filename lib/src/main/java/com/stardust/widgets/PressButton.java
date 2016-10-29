package com.stardust.widgets;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Stardust on 2016/8/14.
 */
public class PressButton extends TextView {

    private boolean mIsPressed = false;
    private int mPressedColor = Color.GREEN;
    private int mUnpressedColor;

    public PressButton(Context context) {
        super(context);
        init();
    }

    public PressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PressButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void setColor(int colorChecked, int colorUnchecked) {
        setPressedColor(colorChecked);
        setNotPressedColor(colorUnchecked);
    }

    public void setPressedColor(int color) {
        if (mPressedColor == color)
            return;
        mPressedColor = color;
    }


    public void setNotPressedColor(int color) {
        if (mUnpressedColor == color)
            return;
        mUnpressedColor = color;
    }

    public void setState(boolean isPressed) {
        if (mIsPressed == isPressed) {
            return;
        }
        mIsPressed = isPressed;
        setPressed(mIsPressed);
    }

    private void setTextColor(boolean isPressed) {
        if (isPressed) {
            setTextColor(mPressedColor);
        } else {
            setTextColor(mUnpressedColor);
        }
    }

    private void init() {
        mUnpressedColor = getCurrentTextColor();
        setClickable(true);
    }

    public void setPressed(boolean b) {
        super.setPressed(b);
        setTextColor(b);
    }
}
