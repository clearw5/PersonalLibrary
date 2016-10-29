package com.stardust.theme;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Stardust on 2016/8/14.
 */
public class ThemeColorScrollView extends ScrollView implements ThemeColorManager.ThemeColorWidget {

    private int mFadingEdgeColor;

    public ThemeColorScrollView(Context context) {
        super(context);
        init();
    }

    public ThemeColorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThemeColorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ThemeColorScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mFadingEdgeColor = super.getSolidColor();
        ThemeColorManager.ThemeColorWidgetManager.add(this);
    }

    public int getSolidColor() {
        return mFadingEdgeColor;
    }

    @Override
    public void setThemeColor(int color) {
        mFadingEdgeColor = color;
        ScrollingViewThemeColorManager.setEdgeGlowColor(this, mFadingEdgeColor);
        invalidate();
    }
}
