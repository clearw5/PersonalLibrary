package com.stardust.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.stardust.tool.MathTool;
import com.stardust.tool.ViewTool;

/**
 * Created by Stardust on 2016/7/29.
 */
public class RoundedImageView extends ImageView {

    private float factor = 0.1f;

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setImageBitmap(Bitmap bitmap) {
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedDrawable.setAntiAlias(true);
        roundedDrawable.setCornerRadius(((float) MathTool.hypotenuse(bitmap.getWidth(), bitmap.getHeight())) * 0.05f);
        super.setImageDrawable(roundedDrawable);
    }

    public void setImageDrawable(Drawable drawable) {
        if (drawable instanceof RoundedBitmapDrawable) {
            super.setImageDrawable(drawable);
            return;
        }
        setImageBitmap(ViewTool.Drawable2Bitmap(drawable));
    }


}
