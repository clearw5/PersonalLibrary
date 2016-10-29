package com.stardust.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.stardust.tool.BitmapTool;

/**
 * Created by Stardust on 2016/8/12.
 */
public class CheckImageButton extends ImageButton implements View.OnClickListener, View.OnTouchListener {

    private static final String TAG = "CheckImageButton";
    private BitmapDrawable mCheckedDrawable;
    private BitmapDrawable mUncheckedDrawable;
    private boolean mIsChecked = false;
    private int mCheckedColor = Color.GREEN;
    private boolean mIsCheckedImageDrawable = false;
    private boolean mWillUnchecked = false;
    private boolean mIsForcePress = false;
    private boolean mForcePressed = false;
    private int mUncheckedColor = Color.TRANSPARENT;
    private OnClickListener mOnClickListener;
    private OnTouchListener mOnTouchListener;

    public CheckImageButton(Context context) {
        super(context);
        init();
    }

    public CheckImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CheckImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        createCheckDrawable();
    }


    public void setUncheckedColor(int color) {
        if (mUncheckedColor == color)
            return;
        mUncheckedColor = color;
        mUncheckedDrawable = BitmapTool.setBitmapDrawableColor(getResources(), mUncheckedDrawable, mUncheckedColor);
    }

    public void setState(boolean isChecked) {
        if (mIsChecked == isChecked) {
            return;
        }
        Log.i(TAG, "set state=" + mIsChecked);
        mIsChecked = isChecked;
        setImageDrawable(mIsChecked);
        if (mIsForcePress && !isChecked) {
            mIsForcePress = false;
        }
        super.setPressed(mIsChecked);
    }


    public void setOnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
    }

    public void setOnTouchListener(OnTouchListener listener) {
        mOnTouchListener = listener;
    }

    private void setImageDrawable(boolean isChecked) {
        if (isChecked == mIsCheckedImageDrawable) {
            return;
        }
        mIsCheckedImageDrawable = isChecked;
        if (mIsCheckedImageDrawable) {
            if (mCheckedDrawable == null)
                createCheckDrawable();
            setImageDrawable(mCheckedDrawable);
        } else {
            setImageDrawable(mUncheckedDrawable);
        }
    }

    private void createCheckDrawable() {
        mCheckedDrawable = BitmapTool.setBitmapDrawableColor(getResources(), mUncheckedDrawable, mCheckedColor);
    }

    private void init() {
        mUncheckedDrawable = (BitmapDrawable) getDrawable();
        super.setOnTouchListener(this);
        super.setOnClickListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch:" + MotionEvent.actionToString(event.getAction()));
        /*
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setImageDrawable(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            setState(!mIsChecked);
        }
        if (mOnTouchListener != null)
            return mOnTouchListener.onTouch(v, event);
        return false;
        */
        return false;
    }

    /*
        public void setPressed(boolean b) {
            Log.i(TAG, "setPressed=" + b + " checked=" + mIsChecked + "willUnchecked=" + mWillUnchecked);
            if (b && !mIsChecked) {
                mIsChecked = true;
                setImageDrawable(true);
                super.setPressed(true);
                return;
            }
            if (!b && mIsChecked && mWillUnchecked) {
                mIsChecked = false;
                mWillUnchecked = false;
                setImageDrawable(false);
                super.setPressed(false);
                return;
            }
            if (b && mIsChecked) {
                mWillUnchecked = true;
            }
        }
    */

    public void setPressed(boolean b) {
        Log.i(TAG, "set press=" + b);
        if (mIsForcePress) {
            Log.i(TAG, "force press");
            super.setPressed(true);
            setImageDrawable(true);
        } else {
            super.setPressed(b);
            setImageDrawable(b);
        }
    }

    @Override
    public void onClick(View v) {
        mIsForcePress = !mIsForcePress;
        Log.i(TAG, "on click:force press=" + mForcePressed);
        setState(!mIsChecked);
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
    }
}
