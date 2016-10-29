package com.stardust.theme;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;


/**
 * Created by Stardust on 2016/8/7.
 */
public class ThemeColorSwitch extends SwitchCompat {
    public ThemeColorSwitch(Context context) {
        super(context);
        init();
    }

    public ThemeColorSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThemeColorSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ThemeColorManager.addSwitch(this);
    }
}
