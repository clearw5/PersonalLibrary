package com.stardust.tool;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

/**
 * 和绘制、画布、屏幕、显示等有关的工具
 * 注意：某些方法需要调用setActivity才能使用
 * 这是方便一些不方便获取Activity的类的使用
 * 因此最后在Activity的开始调用setActivity
 */
public class ViewTool {

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return 宽度的像素值
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度，不包括ActionBar部分和状态栏宽度
     * 不包括ActionBar部分和状态栏宽度
     *
     * @param activity
     * @return 高度的像素值
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return 宽度的像素值
     */
    public static int getScreenWidth() {
        return getScreenWidth(ContextReservoir.getDefaultActivity());
    }

    /**
     * 获取屏幕高度，不包括ActionBar部分和状态栏宽度
     * 不包括ActionBar部分和状态栏宽度
     *
     * @return 屏幕高度像素值
     */
    public static int getScreenHeight() {
        return getScreenHeight(ContextReservoir.getDefaultActivity());
    }

    /**
     * 获取设备状态栏高度
     *
     * @return 状态栏高度的像素大小(px)，获取失败时返回-1
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = -1;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = ContextReservoir.getDefaultContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * dp转px
     *
     * @param dipValue dp值
     * @return 像素大小px
     */
    public static int dip2px(float dipValue) {
        final float scale = ContextReservoir.getDefaultActivity().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = ContextReservoir.getDefaultContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = ContextReservoir.getDefaultContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取字符串绘制宽度
     *
     * @param str
     * @param p   绘制字符串的画笔
     * @return
     */
    public static int getStringWidth(String str, Paint p) {
        Rect rect = new Rect();
        p.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    /**
     * 获取字符串绘制高度
     *
     * @param str
     * @param p   绘制字符串的画笔
     * @return
     */
    public static int getStringHeight(String str, Paint p) {
        Rect rect = new Rect();
        p.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    /**
     * bitmap位图按比例缩放
     *
     * @param bitmap
     * @param widthScale  宽度比例，1.0为原始大小，大于1为放大，小于1为缩小
     * @param heightScale 高度比例，1.0为原始大小，大于1为放大，小于1为缩小
     * @return
     */
    public static Bitmap zoom(Bitmap bitmap, float widthScale, float heightScale) {
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * bitmap位图指定大小缩放
     *
     * @param bitmap
     * @param width  指定缩放后位图宽度
     * @param height 指定缩放后位图高度
     * @return
     */
    public static Bitmap zoom(Bitmap bitmap, int width, int height) {
        float widthScale = (float) width / bitmap.getWidth();
        float heightScale = (float) height / bitmap.getHeight();
        return zoom(bitmap, widthScale, heightScale);
    }

    /**
     * 将位图bitmap转换为Drawable
     *
     * @param context
     * @param bitmap
     * @return
     */
    public static Drawable Bitmap2Drawable(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * 读取文件路径指向的图片文件并转换为Drawable
     *
     * @param context
     * @param path    文件的绝对路径
     * @return
     */
    public static Drawable path2Drawable(Context context, String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    private static final int Build_VERSION_CODES_KITKAT = 19;
    private static final int FLAG_TRANSLUCENT_STATUS = 0x4000000;
    private static final int FLAG_TRANSLUCENT_NAVIGATION = 0x8000000;

    /**
     * 将Activity主题设置为透明状态栏主题
     * 只有API19以上才有效
     *
     * @param activity
     */
    public static void initializeTranslucentTheme(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build_VERSION_CODES_KITKAT) {
            // 透明状态栏
            activity.getWindow().setFlags(FLAG_TRANSLUCENT_STATUS,
                    FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            activity.getWindow().setFlags(FLAG_TRANSLUCENT_NAVIGATION,
                    FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * @param id
     * @return
     */
    public static Bitmap getResourceBitmap(int id) {
        return BitmapFactory.decodeResource(ContextReservoir.getDefaultContext().getResources(), id);
    }

    public static Resources getResources() {
        return ContextReservoir.getDefaultContext().getResources();
    }

    public static void setMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) params).setMargins(left, top, right, bottom);
        }
        view.requestLayout();
    }

    public static Bitmap View2Bitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static boolean View2PictureFile(View view, String path, Bitmap.CompressFormat compressFormat) {
        File file = new File(path);
        try {
            Bitmap bitmap = loadBitmapFromView(view);
            if (bitmap == null)
                return false;
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(compressFormat, 100, fos);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e);
            return false;
        }
    }

    public static boolean bitmap2file(Bitmap bitmap, String path) {
        FileTool.mkdirs(path);
        File file = new File(path);
        try {
            if (bitmap == null)
                return false;
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(e);
            return false;
        }

    }

    public static boolean View2PngFile(View view, String path) {
        return View2PictureFile(view, path, Bitmap.CompressFormat.PNG);
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredWidth(), Bitmap.Config.ARGB_8888);
        b.eraseColor(Color.WHITE);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    public static ImageView bitmap2ImageView(Context context, Bitmap bitmap) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

    public static boolean removeFromParent(View child) {
        if (child.getParent() != null && child.getParent() instanceof ViewGroup) {
            ((ViewGroup) child.getParent()).removeView(child);
            return true;
        }
        return false;
    }

    public static Bitmap Drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}