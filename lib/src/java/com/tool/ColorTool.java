package com.stardust.tool;

import android.graphics.Color;

/**
 * Created by Stardust on 2016/8/8.
 */
public class ColorTool {

    public static int makeAlpha(int alpha, int color) {
        return Color.argb(0x66, Color.red(color), Color.green(color), Color.blue(color));
    }

    public static String toNoTransparentString(int expressionColor) {
        String str = Integer.toHexString(expressionColor);
        while (str.length() < 6) {
            str = "0" + str;
        }
        if (str.length() > 6) {
            str = str.substring(str.length() - 6);
        }
        return str;
    }
}
