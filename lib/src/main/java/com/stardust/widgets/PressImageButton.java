package com.stardust.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.stardust.tool.BitmapTool;

/**
 * Created by Stardust on 2016/8/12.
 */
public class PressImageButton extends ImageButton {

    private BitmapDrawable mPressedDrawable;
    private BitmapDrawable mUnpressedDrawable;
    private int mPressedColor = Color.GREEN;
    private int mUnpressedColor = Color.TRANSPARENT;
    private boolean isPressed;

    public PressImageButton(Context context) {
        super(context);
        init();
    }

    public PressImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PressImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PressImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setPressed(boolean b) {
        super.setPressed(b);
        setImageDrawable(b);
    }

    public void setImageDrawable(Drawable drawable) {
        if (!(drawable instanceof BitmapDrawable))
            throw new IllegalArgumentException();
        mUnpressedDrawable = (BitmapDrawable) drawable;
        mPressedDrawable = null;
        super.setImageDrawable(drawable);
    }

    public void setColor(int colorChecked, int colorUnchecked) {
        setPressedColor(colorChecked);
        setUnpressedColor(colorUnchecked);
    }

    public void setPressedColor(int color) {
        if (mPressedColor == color)
            return;
        mPressedColor = color;
        mPressedDrawable = null;
    }


    public void setUnpressedColor(int color) {
        if (mUnpressedColor == color)
            return;
        mUnpressedColor = color;
        setImageDrawable(BitmapTool.setBitmapDrawableColor(getResources(), mUnpressedDrawable, color));
    }

    private void setImageDrawable(boolean b) {
        if (isPressed == b)
            return;
        isPressed = b;
        if (!isPressed) {
            super.setImageDrawable(mUnpressedDrawable);
        } else {
            if (mPressedDrawable == null)
                createPressedDrawable();
            super.setImageDrawable(mPressedDrawable);
        }
    }

    private void createPressedDrawable() {
        mPressedDrawable = BitmapTool.setBitmapDrawableColor(getResources(), mUnpressedDrawable, mPressedColor);
    }

    private void init() {
        mUnpressedDrawable = (BitmapDrawable) getDrawable();
    }
}
