package com.stardust.tool;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Stardust on 2016/5/21.
 * <p>
 * 字体类，用于字体使用的辅助
 */
public class Typefaces {

    /**
     * 以下是一些字体类型
     */
    public static final int EUCLID_BI = 0;
    public static final int SATISFY_REGULAR = 1;
    public static final int EUCLID_X = 2;

    private static Typeface euclid_bi;
    private static Typeface satisfy_regular;
    private static Typeface euclid_x;

    /**
     * 获取字体
     *
     * @param context
     * @param type    字体类型
     * @return
     */
    public static Typeface getTypeface(Context context, int type) {
        if (type == EUCLID_BI) {
            if (euclid_bi == null)
                euclid_bi = Typeface.createFromAsset(context.getAssets(),
                        "fonts/euclid_bi_customization.ttf");
            return euclid_bi;
        }
        if (type == SATISFY_REGULAR) {
            if (satisfy_regular == null)
                satisfy_regular = Typeface.createFromAsset(context.getAssets(),
                        "fonts/Satisfy-Regular.ttf");
            return satisfy_regular;
        }
        if (type == EUCLID_X) {
            if (euclid_x == null) {
                euclid_x = Typeface.createFromAsset(context.getAssets(), "fonts/euclid_x.ttf");
            }
            return euclid_x;
        }
        throw new IllegalArgumentException("未知字体类型 type=" + type);
    }
}
