package com.stardust.theme;

import android.content.Context;
import android.util.AttributeSet;

import com.stardust.widgets.PressButton;

/**
 * Created by Stardust on 2016/8/14.
 */
public class ThemeColorPressButton extends PressButton implements ThemeColorManager.ThemeColorWidget {

    public ThemeColorPressButton(Context context) {
        super(context);
        init();
    }

    public ThemeColorPressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThemeColorPressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ThemeColorPressButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        ThemeColorManager.ThemeColorWidgetManager.add(this);
    }

    @Override
    public void setThemeColor(int color) {
        setPressedColor(color);
    }
}
