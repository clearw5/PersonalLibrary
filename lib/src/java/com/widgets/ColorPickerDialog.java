package com.stardust.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stardust.tool.ViewTool;

/**
 * 一个拾色器，支持按色环颜色取色，或者输入RGB取色
 */
public class ColorPickerDialog extends Dialog {

    private final boolean debug = false;
    private final String TAG = "ColorPicker";

    private static final String[] RGB_Text = {"R: ", "G: ", "B: "};
    private static final int RED = 0;
    private static final int BLUE = 2;
    private static final int GREEN = 1;

    Context context;
    private String title;
    private int initialColor;
    private OnColorChangedListener mListener;
    private LinearLayout mView;
    //显示颜色的RGB值（16进制和10进制）的编辑框的View
    private ColorValueEditView R_View, G_View, B_View;
    private ColorPickerView colorPickerView;

    /**
     * 显示一个对话框选取颜色，标题默认为"选取颜色"
     *
     * @param context
     * @param defColor               初始颜色
     * @param onColorChangedListener 监听者
     */
    public static void pickColor(Context context, int defColor,
                                 OnColorChangedListener onColorChangedListener) {
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(context,
                defColor, "选取颜色", onColorChangedListener);
        colorPickerDialog.show();
    }

    /**
     * 初始颜色黑色
     *
     * @param context
     * @param title    对话框标题
     * @param listener 回调
     */
    public ColorPickerDialog(Context context, String title,
                             OnColorChangedListener listener) {
        this(context, Color.BLACK, title, listener);
    }

    /**
     * @param context
     * @param initialColor 初始颜色
     * @param title        标题
     * @param listener     回调
     */
    public ColorPickerDialog(Context context, int initialColor, String title,
                             OnColorChangedListener listener) {
        super(context);
        this.context = context;
        mListener = listener;
        this.initialColor = initialColor;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int height = (int) (ViewTool.getScreenHeight() * 0.5f);
        int width = (int) (ViewTool.getScreenWidth() * 0.7f);
        colorPickerView = new ColorPickerView(context, height, width);
        mView = new LinearLayout(getContext());
        mView.setOrientation(LinearLayout.VERTICAL);
        mView.setGravity(Gravity.CENTER);
        mView.addView(colorPickerView);
        R_View = new ColorValueEditView(getContext(), RED);
        G_View = new ColorValueEditView(getContext(), GREEN);
        B_View = new ColorValueEditView(getContext(), BLUE);
        mView.addView(R_View);
        mView.addView(G_View);
        mView.addView(B_View);

        setContentView(mView);
        setTitle(title);
        updateRGBDisplay();
    }

    //根据色环颜色更新RGB值的编辑框的显示
    private void updateRGBDisplay() {
        int color = colorPickerView.mCenterPaint.getColor();
        R_View.setColorValue(Color.red(color));
        G_View.setColorValue(Color.green(color));
        B_View.setColorValue(Color.blue(color));
    }

    //根据RGB值更新色环颜色
    private void updateColorPicker() {
        if (R_View != null && G_View != null && B_View != null) {
            colorPickerView.mCenterPaint.setColor(Color.rgb(R_View.colorValue,
                    G_View.colorValue, B_View.colorValue));
            colorPickerView.invalidate();
        }
    }

    private class ColorValueEditView extends LinearLayout {

        //用来标记是R、G、B中的哪一个类型, 即颜色值类型
        private int colorType;
        private EditText dec, hex;
        private int colorValue;
        //标记是否是用户主动修改了颜色值
        boolean isChangedPositive = false;

        private void initialize() {
            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(Gravity.CENTER);
            setPadding(ViewTool.dip2px(20), 0, 0, 0);
            TextView textView = new TextView(getContext());
            textView.setText(RGB_Text[colorType]);
            addView(textView);
            dec = new EditText(getContext());
            LayoutParams layoutParams = new LayoutParams(0,
                    LayoutParams.WRAP_CONTENT, 1.0f);
            dec.setLayoutParams(layoutParams);
            dec.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        colorValue = Integer.parseInt(s.toString());
                        if (!isChangedPositive) {
                            updateHexEditText();
                        } else {
                            isChangedPositive = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            hex = new EditText(getContext());
            hex.setLayoutParams(layoutParams);
            hex.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        String colorString = s.toString();
                        //只有以0x开头才是正确格式
                        if (colorString.length() > 2 && colorString.startsWith("0x")) {
                            colorValue = Integer.parseInt(
                                    colorString.substring(2), 16);
                            if (!isChangedPositive) {
                                updateDecEditText();
                            } else {
                                isChangedPositive = false;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

            updateDecEditText();
            updateHexEditText();
            addView(dec);
            addView(hex);
        }

        /**
         * 设置颜色 这将不会更新色环颜色
         *
         * @param c
         */
        public void setColorValue(int c) {
            colorValue = c;
            updateDecEditText();
            updateHexEditText();
        }

        private void updateHexEditText() {
            isChangedPositive = true;
            hex.setText("0x" + Integer.toHexString(colorValue));
            updateColorPicker();
        }

        private void updateDecEditText() {
            isChangedPositive = true;
            dec.setText(String.valueOf(colorValue));
            updateColorPicker();
        }

        /**
         * 初始颜色值类型为RED，颜色值为0
         *
         * @param context
         */
        public ColorValueEditView(Context context) {
            this(context, RED, 0);
        }

        /**
         * 初始颜色值为0
         *
         * @param context
         * @param colorType 颜色类型，包括RED，BLUE，GREEN，表示这个颜色值编辑框编辑的是RGB中哪个颜色值
         */
        public ColorValueEditView(Context context, int colorType) {
            this(context, colorType, 0);
        }

        /**
         * @param context
         * @param colorType  颜色类型，包括RED，BLUE，GREEN，表示这个颜色值编辑框编辑的是RGB中哪个颜色值
         * @param colorValue 初始颜色值，为0~255
         */
        public ColorValueEditView(Context context, int colorType, int colorValue) {
            super(context);
            this.colorType = colorType;
            this.colorValue = colorValue;
            if (colorType < RED || colorType >  BLUE)
                throw new IllegalArgumentException("未知颜色值类型 colorType=" + colorType);
            initialize();
        }

    }

    private class ColorPickerView extends View {

        private Paint mPaint;// 渐变色环画笔
        private Paint mCenterPaint;// 中间圆画笔
        private Paint mTextPaint;// 文字画笔
        private Paint mLinePaint;// 分隔线画笔
        private Paint mRectPaint;// 渐变方块画笔

        private Shader rectShader;// 渐变方块渐变图像
        private float rectLeft;// 渐变方块左x坐标
        private float rectTop;// 渐变方块右x坐标
        private float rectRight;// 渐变方块上y坐标
        private float rectBottom;// 渐变方块下y坐标

        private final int[] mCircleColors;// 渐变色环颜色
        private final int[] mRectColors;// 渐变方块颜色

        private int mHeight;// View高
        private int mWidth;// View宽
        private float r;// 色环半径(paint中部)
        private float centerRadius;// 中心圆半径

        private boolean downInCircle = true;// 按在渐变环上
        private boolean downInRect;// 按在渐变方块上
        private boolean highlightCenter;// 高亮
        private boolean highlightCenterLittle;// 微亮

        public ColorPickerView(Context context, int height, int width) {
            super(context);
            this.mHeight = height - 36;
            this.mWidth = width;
            setMinimumHeight(height - 36);
            setMinimumWidth(width);

            // 渐变色环参数
            mCircleColors = new int[]{0xFFFF0000, 0xFFFF00FF, 0xFF0000FF,
                    0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000};
            Shader s = new SweepGradient(0, 0, mCircleColors, null);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setShader(s);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(50);
            r = width / 2 * 0.7f - mPaint.getStrokeWidth() * 0.5f;

            // 中心圆参数
            mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCenterPaint.setColor(initialColor);
            mCenterPaint.setStrokeWidth(5);
            centerRadius = (r - mPaint.getStrokeWidth() / 2) * 0.7f;

            mTextPaint = new Paint();
            mTextPaint.setTextSize(ViewTool.dip2px(20));
            // 边框参数
            mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mLinePaint.setColor(Color.parseColor("#72A1D1"));
            mLinePaint.setStrokeWidth(4);

            // 黑白渐变参数
            mRectColors = new int[]{0xFF000000, mCenterPaint.getColor(),
                    0xFFFFFFFF};
            mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mRectPaint.setStrokeWidth(5);
            rectLeft = -r - mPaint.getStrokeWidth() * 0.5f;
            rectTop = r + mPaint.getStrokeWidth() * 0.5f
                    + mLinePaint.getStrokeMiter() * 0.5f + 15;
            rectRight = r + mPaint.getStrokeWidth() * 0.5f;
            rectBottom = rectTop + 50;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // 移动中心
            canvas.translate(mWidth / 2, mHeight / 2 - 50);
            // 画中心圆
            canvas.drawCircle(0, 0, centerRadius, mCenterPaint);
            // 画中心园的确定字样
            canvas.drawText("OK",
                    -ViewTool.getStringWidth("OK", mTextPaint) / 2,
                    ViewTool.getStringHeight("OK", mTextPaint) / 2,
                    mTextPaint);
            // 是否显示中心圆外的小圆环
            if (highlightCenter || highlightCenterLittle) {
                int c = mCenterPaint.getColor();
                mCenterPaint.setStyle(Paint.Style.STROKE);
                if (highlightCenter) {
                    mCenterPaint.setAlpha(0xFF);
                } else if (highlightCenterLittle) {
                    mCenterPaint.setAlpha(0x90);
                }
                canvas.drawCircle(0, 0,
                        centerRadius + mCenterPaint.getStrokeWidth(),
                        mCenterPaint);

                mCenterPaint.setStyle(Paint.Style.FILL);
                mCenterPaint.setColor(c);
            }
            // 画色环
            canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
            // 画黑白渐变块
            if (downInCircle) {
                mRectColors[1] = mCenterPaint.getColor();
            }
            rectShader = new LinearGradient(rectLeft, 0, rectRight, 0,
                    mRectColors, null, Shader.TileMode.MIRROR);
            mRectPaint.setShader(rectShader);
            canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom,
                    mRectPaint);
            float offset = mLinePaint.getStrokeWidth() / 2;
            canvas.drawLine(rectLeft - offset, rectTop - offset * 2, rectLeft
                    - offset, rectBottom + offset * 2, mLinePaint);// 左
            canvas.drawLine(rectLeft - offset * 2, rectTop - offset, rectRight
                    + offset * 2, rectTop - offset, mLinePaint);// 上
            canvas.drawLine(rectRight + offset, rectTop - offset * 2, rectRight
                    + offset, rectBottom + offset * 2, mLinePaint);// 右
            canvas.drawLine(rectLeft - offset * 2, rectBottom + offset,
                    rectRight + offset * 2, rectBottom + offset, mLinePaint);// 下
            super.onDraw(canvas);
        }

        @Override
        public boolean performClick() {
            // TODO Auto-generated method stub
            return super.performClick();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX() - mWidth / 2;
            float y = event.getY() - mHeight / 2 + 50;
            boolean inCircle = inColorCircle(x, y, r + mPaint.getStrokeWidth()
                    / 2, r - mPaint.getStrokeWidth() / 2);
            boolean inCenter = inCenter(x, y, centerRadius);
            boolean inRect = inRect(x, y);
            performClick();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downInCircle = inCircle;
                    downInRect = inRect;
                    highlightCenter = inCenter;
                case MotionEvent.ACTION_MOVE:
                    if (downInCircle && inCircle) {// down按在渐变色环内, 且move也在渐变色环内
                        float angle = (float) Math.atan2(y, x);
                        float unit = (float) (angle / (2 * Math.PI));
                        if (unit < 0) {
                            unit += 1;
                        }
                        mCenterPaint
                                .setColor(interpCircleColor(mCircleColors, unit));
                        updateRGBDisplay();
                        if (debug)
                            Log.v(TAG, "色环内, 坐标: " + x + "," + y);
                    } else if (downInRect && inRect) {// down在渐变方块内, 且move也在渐变方块内
                        mCenterPaint.setColor(interpRectColor(mRectColors, x));
                        updateRGBDisplay();
                    }
                    if (debug)
                        Log.v(TAG, "[MOVE] 高亮: " + highlightCenter + "微亮: "
                                + highlightCenterLittle + " 中心: " + inCenter);
                    if ((highlightCenter && inCenter)
                            || (highlightCenterLittle && inCenter)) {// 点击中心圆,
                        // 当前移动在中心圆
                        highlightCenter = true;
                        highlightCenterLittle = false;
                    } else if (highlightCenter || highlightCenterLittle) {// 点击在中心圆,
                        // 当前移出中心圆
                        highlightCenter = false;
                        highlightCenterLittle = true;
                    } else {
                        highlightCenter = false;
                        highlightCenterLittle = false;
                    }
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if (highlightCenter && inCenter) {// 点击在中心圆, 且当前启动在中心圆
                        if (mListener != null) {
                            mListener.colorChanged(mCenterPaint.getColor());
                            ColorPickerDialog.this.dismiss();
                        }
                    }
                    if (downInCircle) {
                        downInCircle = false;
                    }
                    if (downInRect) {
                        downInRect = false;
                    }
                    if (highlightCenter) {
                        highlightCenter = false;
                    }
                    if (highlightCenterLittle) {
                        highlightCenterLittle = false;
                    }
                    invalidate();
                    break;
            }
            return true;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(mWidth, mHeight);
        }

        /**
         * 坐标是否在色环上
         *
         * @param x         坐标
         * @param y         坐标
         * @param outRadius 色环外半径
         * @param inRadius  色环内半径
         * @return
         */
        private boolean inColorCircle(float x, float y, float outRadius,
                                      float inRadius) {
            double outCircle = Math.PI * outRadius * outRadius;
            double inCircle = Math.PI * inRadius * inRadius;
            double fingerCircle = Math.PI * (x * x + y * y);
            if (fingerCircle < outCircle && fingerCircle > inCircle) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 坐标是否在中心圆上
         *
         * @param x            坐标
         * @param y            坐标
         * @param centerRadius 圆半径
         * @return
         */
        private boolean inCenter(float x, float y, float centerRadius) {
            double centerCircle = Math.PI * centerRadius * centerRadius;
            double fingerCircle = Math.PI * (x * x + y * y);
            if (fingerCircle < centerCircle) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 坐标是否在渐变色中
         *
         * @param x
         * @param y
         * @return
         */
        private boolean inRect(float x, float y) {
            if (x <= rectRight && x >= rectLeft && y <= rectBottom
                    && y >= rectTop) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 获取圆环上颜色
         *
         * @param colors
         * @param unit
         * @return
         */
        private int interpCircleColor(int colors[], float unit) {
            if (unit <= 0) {
                return colors[0];
            }
            if (unit >= 1) {
                return colors[colors.length - 1];
            }

            float p = unit * (colors.length - 1);
            int i = (int) p;
            p -= i;

            // now p is just the fractional part [0...1) and i is the index
            int c0 = colors[i];
            int c1 = colors[i + 1];
            int a = ave(Color.alpha(c0), Color.alpha(c1), p);
            int r = ave(Color.red(c0), Color.red(c1), p);
            int g = ave(Color.green(c0), Color.green(c1), p);
            int b = ave(Color.blue(c0), Color.blue(c1), p);

            return Color.argb(a, r, g, b);
        }

        /**
         * 获取渐变块上颜色
         *
         * @param colors
         * @param x
         * @return
         */
        private int interpRectColor(int colors[], float x) {
            int a, r, g, b, c0, c1;
            float p;
            if (x < 0) {
                c0 = colors[0];
                c1 = colors[1];
                p = (x + rectRight) / rectRight;
            } else {
                c0 = colors[1];
                c1 = colors[2];
                p = x / rectRight;
            }
            a = ave(Color.alpha(c0), Color.alpha(c1), p);
            r = ave(Color.red(c0), Color.red(c1), p);
            g = ave(Color.green(c0), Color.green(c1), p);
            b = ave(Color.blue(c0), Color.blue(c1), p);
            return Color.argb(a, r, g, b);
        }

        private int ave(int s, int d, float p) {
            return s + Math.round(p * (d - s));
        }
    }

    /**
     * 回调接口
     *
     * @author <a href="clarkamx@gmail.com">LynK</a>
     *         <p/>
     *         Create on 2012-1-6 上午8:21:05
     */
    public interface OnColorChangedListener {
        /**
         * 回调函数
         *
         * @param color 选中的颜色
         */
        void colorChanged(int color);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        super.setTitle(title);
    }

    public int getInitialColor() {
        return initialColor;
    }

    public void setInitialColor(int initialColor) {
        this.initialColor = initialColor;
    }

    public OnColorChangedListener getmListener() {
        return mListener;
    }

    public void setOnColorChangedListener(OnColorChangedListener mListener) {
        this.mListener = mListener;
    }
}
