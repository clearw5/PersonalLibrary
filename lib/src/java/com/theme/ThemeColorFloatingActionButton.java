package com.stardust.theme;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

/**
 * Created by Stardust on 2016/8/16.
 */
public class ThemeColorFloatingActionButton extends FloatingActionButton implements ThemeColorManager.ThemeColorWidget {
    public ThemeColorFloatingActionButton(Context context) {
        super(context);
        init();
    }

    public ThemeColorFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThemeColorFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ThemeColorManager.ThemeColorWidgetManager.add(this);
    }

    @Override
    public void setThemeColor(int color) {
        setBackgroundColor(color);
    }
}
