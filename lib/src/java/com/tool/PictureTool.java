package com.stardust.tool;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Stardust on 2016/8/9.
 */
public class PictureTool {

    public static BitmapDrawable setBitmapDrawableColor(Resources res, BitmapDrawable drawable, int color) {
        BitmapDrawable outDrawable = new BitmapDrawable(res, setBitmapColor(drawable.getBitmap(), color));
        return outDrawable;
    }

    public static Bitmap setBitmapColor(Bitmap bitmap, int color) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);

        Paint maskedPaint = new Paint();
        maskedPaint.setColor(color);
        maskedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        canvas.drawRect(0, 0, width, height, maskedPaint);

        return outBitmap;
    }
}
