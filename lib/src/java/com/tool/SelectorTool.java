package com.stardust.tool;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by Stardust on 2016/8/8.
 */
public class SelectorTool {

    public static StateListDrawable createPressSelector(int color,int colorPressed){
        StateListDrawable drawable = new StateListDrawable();
         drawable.addState(new int[]{android.R.attr.state_empty}, new ColorDrawable(color));
        drawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(colorPressed));
        return drawable;
    }
}
