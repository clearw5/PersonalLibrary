package com.stardust.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Map;

/**
 * Created by Stardust on 2016/8/13.
 */
public class RingButtonLayout extends LinearLayout {

    private static final int PRESSED_NONE = -2;
    private static final int PRESSED_CENTER = -1;
    private static final String TAG = "RingButtonLayout";
    private Paint mRingPaint = new Paint();
    private Paint mDividerPaint = new Paint();
    private Paint mBackgroundPaint = new Paint();
    private Paint mCenterPressedPaint = new Paint();
    private Paint mButtonPressedPaint = new Paint();
    private Map<Integer, OnClickListener> mListener;

    private int mStartAngle = 0;
    private int mPressedButton = PRESSED_NONE;
    private float mInnerRadius = -1;
    private float mRadius;
    private int mCenterId = 0;
    private int mButtonCount = -1;
    private View mCenterView = null;
    private int mCenterViewIndex = -1;

    public boolean willDrawRingDivider() {
        return mDrawRingDivider;
    }

    public void setWillDrawDivider(boolean mDrawRingDivider) {
        this.mDrawRingDivider = mDrawRingDivider;
    }

    private boolean mDrawRingDivider = true;

    private int mDividerColor = Color.GRAY;
    private int mDividerWidth = 1;

    private int mRingColor = Color.WHITE;
    private int mButtonPressedColor = Color.GRAY;
    private int mBackgroundColor = Color.WHITE;
    private int mCenterPressedColor = Color.GRAY;
    private float mInnerRadiusRadio = 0.5f;

    public int getDividerWidth() {
        return mDividerWidth;
    }

    public void setDividerWidth(int mDividerWidth) {
        this.mDividerWidth = mDividerWidth;
        mDividerPaint.setStrokeWidth(mDividerWidth);
    }

    public float getInnerRadius() {
        if (mInnerRadiusRadio >= 0) {
            mInnerRadius = mRadius * mInnerRadiusRadio;
        }
        return mInnerRadius;
    }

    public void setInnerRadius(float innerRadius) {
        if (innerRadius < 0)
            throw new IllegalArgumentException();
        this.mInnerRadius = innerRadius;
        mInnerRadiusRadio = -1;
    }

    public void setInnerRadiusRadio(float radio) {
        if (radio < 0)
            throw new IllegalArgumentException();
        mInnerRadiusRadio = radio;
    }

    public int getRingColor() {
        return mRingColor;
    }

    public void setRingColor(int mRingColor) {
        this.mRingColor = mRingColor;
        mBackgroundPaint.setColor(mRingColor);
    }

    public int getButtonPressedColor() {
        return mButtonPressedColor;
    }

    public void setButtonPressedColor(int mButtonPressedColor) {
        this.mButtonPressedColor = mButtonPressedColor;
        mButtonPressedPaint.setColor(mButtonPressedColor);
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
        mBackgroundPaint.setColor(mBackgroundColor);
    }

    public int getCenterPressedColor() {
        return mCenterPressedColor;
    }

    public void setCenterPressedColor(int mCenterPressedColor) {
        this.mCenterPressedColor = mCenterPressedColor;
        mCenterPressedPaint.setColor(mCenterPressedColor);
    }

    public int getDividerColor() {
        return mDividerColor;
    }

    public void setDividerColor(int mDividerColor) {
        this.mDividerColor = mDividerColor;
        mDividerPaint.setColor(mDividerColor);
    }

    public int getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(int startAngle) {
        this.mStartAngle = startAngle;
    }

    public void setOnClickListener(int id, OnClickListener listener) {

    }

    public RingButtonLayout(Context context) {
        super(context);
        init();
    }

    public RingButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public RingButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RingButtonLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mRadius = Math.min(getWidth() - getPaddingLeft() - getPaddingRight(), getHeight() - getPaddingBottom() - getPaddingTop()) / 2;
        getInnerRadius();
        int angle = 180 / getButtonCount();
        int h = (int) Math.abs(2 * mInnerRadius * Math.sin(Math.toRadians(angle)));
        double x1 = mInnerRadius * Math.cos(Math.toRadians(angle));
        double x2 = Math.sqrt(mRadius * mRadius - h * h / 4);
        int w = (int) Math.abs(x1 - x2);
        angle += mStartAngle;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != mCenterView) {
                int x = (int) ((mRadius + mInnerRadius) / 2 * Math.cos(Math.toRadians(angle)) - w / 2 + mRadius);
                int y = (int) ((mRadius + mInnerRadius) / 2 * Math.sin(Math.toRadians(angle)) - h / 2 + mRadius);
                child.layout(x, y, x + w, y + h);
                angle += 360 / getButtonCount();
            }
        }
        if (mCenterView != null)
            mCenterView.layout((int) (mRadius - mCenterView.getMeasuredWidth() / 2), (int) (mRadius - mCenterView.getMeasuredHeight() / 2),
                    (int) (mRadius + mCenterView.getMeasuredWidth() / 2), (int) (mRadius + mCenterView.getMeasuredHeight() / 2));
    }

    private void init() {
        mBackgroundPaint.setColor(mBackgroundColor);
        mDividerPaint.setColor(mDividerColor);
        mDividerPaint.setStyle(Paint.Style.STROKE);
        mDividerPaint.setAntiAlias(true);
        mButtonPressedPaint.setColor(mButtonPressedColor);
        mCenterPressedPaint.setColor(mCenterPressedColor);
        mRingPaint.setColor(mRingColor);
        setClickable(true);
        setWillNotDraw(false);
    }

    private int getButtonCount() {
        if (mButtonCount >= 0)
            return mButtonCount;
        mButtonCount = getChildCount();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            if (layoutParams.gravity == Gravity.CENTER) {
                mButtonCount--;
                mCenterView = child;
                mCenterViewIndex = i;
                break;
            }
        }
        return mButtonCount;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int targetView = PRESSED_NONE;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX() - mRadius;
            float y = event.getY() - mRadius;
            if (x * x + y * y < mRadius * mRadius) {
                if (x * x + y * y < mInnerRadius * mInnerRadius) {
                    mPressedButton = PRESSED_CENTER;
                    targetView = mPressedButton;
                    invalidate();
                } else {
                    float angle = (float) Math.toDegrees(Math.atan2(y, x)) - mStartAngle;
                    if (angle < 0) {
                        angle = 360 + angle;
                    }
                    mPressedButton = (int) (angle * getButtonCount() / 360);
                    targetView = mPressedButton;
                    invalidate();
                }
            }
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                mPressedButton = PRESSED_NONE;
                targetView = mPressedButton;
                invalidate();
            }
        }
        if (targetView == PRESSED_CENTER && mCenterView != null) {
            mCenterView.onTouchEvent(event);
        }
        if (targetView >= 0) {
            getButtonViewAt(targetView).dispatchTouchEvent(event);
        }
        Log.i(TAG, "dispatchTouchEvent mPressedButton=" + mPressedButton);
        return super.dispatchTouchEvent(event);
    }

    public View getButtonViewAt(int i) {
        if (mCenterView != null && i >= mCenterViewIndex) {
            return getChildAt(i + 1);
        }
        return getChildAt(i);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.translate(getPaddingLeft(), getPaddingRight());
        //canvas.drawCircle(mRadius, mRadius, mRadius, mRingPaint);
        canvas.drawArc(new RectF(0, 0, 2 * mRadius, 2 * mRadius), 0, 360, false, mDividerPaint);
        if (mPressedButton >= 0) {
            Log.i(TAG, "draw mPressedButton=" + mPressedButton);
            drawArc(canvas, mButtonPressedPaint, mRadius, mRadius, mStartAngle + mPressedButton * 360 / getButtonCount(), 360 / getButtonCount(), mInnerRadius, mRadius - mInnerRadius);
        }
        if (mPressedButton == PRESSED_CENTER) {
            Log.i(TAG, "draw mPreeedButton=" + mPressedButton);
            canvas.drawCircle(mRadius, mRadius, mInnerRadius, mCenterPressedPaint);
        } else {
            canvas.drawCircle(mRadius, mRadius, mInnerRadius, mBackgroundPaint);
        }
        canvas.drawArc(new RectF(mRadius - mInnerRadius, mRadius - mInnerRadius, mRadius + mInnerRadius, mRadius + mInnerRadius), 0, 360, false, mDividerPaint);
        if (mDrawRingDivider) {
            int angle = mStartAngle;
            for (int i = 0; i < getButtonCount(); i++) {
                float x1 = (float) (mRadius + mRadius * Math.cos(Math.toRadians(angle)));
                float y1 = (float) (mRadius - mRadius * Math.sin(Math.toRadians(angle)));
                float x2 = (float) (mRadius + mInnerRadius * Math.cos(Math.toRadians(angle)));
                float y2 = (float) (mRadius - mInnerRadius * Math.sin(Math.toRadians(angle)));
                canvas.drawLine(x1, y1, x2, y2, mDividerPaint);
                angle += 360 / getButtonCount();
            }
        }
        //canvas.translate(-getPaddingLeft(), -getPaddingRight());
        super.onDraw(canvas);
    }

    public static void drawArc(Canvas canvas, Paint paint, float x, float y, int startAngle, int sweepAngle, float innerRadius, float ringWidth) {
        RectF rectF = new RectF(x - (innerRadius + 1 + ringWidth / 2), y - (innerRadius + 1 + ringWidth / 2),
                x + (innerRadius + 1 + ringWidth / 2), y + (innerRadius + 1 + ringWidth / 2));
        //绘制圆环
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setAntiAlias(true);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);

    }

}
