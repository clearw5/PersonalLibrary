package com.stardust.theme;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.stardust.tool.BuildTool;
import com.stardust.tool.ColorTool;
import com.stardust.tool.ViewTool;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Stardust on 2016/5/10.
 * <p/>
 * 管理AppBar的类，主要是同一管理状态栏和标题栏，以便当主题色改变时统一改变主题色
 */

public class ThemeColorManager {

    /**
     * 保存已经记录的状态栏的标题栏
     * 这里用弱引用是因为如果View不再显示后，如果这里用强引用，会导致其内存得不到释放
     */
    private static int mThemeColor = 0;

    public static class BackgroundManager {
        private static List<WeakReference<View>> views = new LinkedList<>();

        public static synchronized void add(View view) {
            views.add(new WeakReference<>(view));
            view.setBackgroundColor(mThemeColor);
        }

        public static synchronized void setColor(int color) {
            Iterator<WeakReference<View>> iterator = views.iterator();
            while (iterator.hasNext()) {
                View view = iterator.next().get();
                if (view != null) {
                    view.setBackgroundColor(color);
                } else {
                    iterator.remove();
                }
            }
        }
    }

    public static class StatusBarManager {
        private static Vector<WeakReference<Activity>> activities = new Vector<>();

        @TargetApi(21)
        public static void add(Activity activity) {
            if (Build.VERSION.SDK_INT >= Build_VERSION_CODES_LOLLPOP) {
                activities.add(new WeakReference<>(activity));
                activity.getWindow().setStatusBarColor(mThemeColor);
            }
        }

        public static void add(View statusBar) {
            initializeStatusBarHeight(statusBar);
            BackgroundManager.add(statusBar);
        }

        @TargetApi(21)
        public static void setColor(int color) {
            Iterator<WeakReference<Activity>> iterator = activities.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next().get();
                if (activity != null) {
                    activity.getWindow().setStatusBarColor(color);
                } else {
                    iterator.remove();
                }
            }
        }
    }

    public static class SwitchManager {

        private static Vector<WeakReference<SwitchCompat>> switchCompats = new Vector<>();

        public static void add(SwitchCompat switchCompat) {
            setColor(switchCompat, mThemeColor);
            switchCompats.add(new WeakReference<>(switchCompat));
        }

        public static void setColor(int color) {
            Iterator<WeakReference<SwitchCompat>> iterator = switchCompats.iterator();
            while (iterator.hasNext()) {
                SwitchCompat switchCompat = iterator.next().get();
                if (switchCompat != null) {
                    setColor(switchCompat, color);
                } else {
                    iterator.remove();
                }
            }
        }

        public static void setColor(SwitchCompat switchCompat, int color) {
            int[][] states = new int[][]{
                    new int[]{-android.R.attr.state_checked},
                    new int[]{android.R.attr.state_checked},
            };

            int[] thumbColors = new int[]{
                    Color.BLACK,
                    color,
            };

            int[] trackColors = new int[]{
                    Color.GRAY,
                    ColorTool.makeAlpha(0x66, color),
            };
            DrawableCompat.setTintList(DrawableCompat.wrap(switchCompat.getThumbDrawable()), new ColorStateList(states, thumbColors));
            DrawableCompat.setTintList(DrawableCompat.wrap(switchCompat.getTrackDrawable()), new ColorStateList(states, trackColors));
        }

    }

    public static class PreferenceCategoryManager {

        private static List<WeakReference<ThemeColorPreferenceCategory>> categories = new LinkedList<>();

        public static void add(ThemeColorPreferenceCategory category) {
            categories.add(new WeakReference<>(category));
            category.setTitleTextColor(mThemeColor);
        }

        public static void setColor(int color) {
            Iterator<WeakReference<ThemeColorPreferenceCategory>> iterator = categories.iterator();
            while (iterator.hasNext()) {
                ThemeColorPreferenceCategory category = iterator.next().get();
                if (category != null) {
                    category.setTitleTextColor(color);
                } else {
                    iterator.remove();
                }
            }
        }
    }

    public static class PaintManager {
        private static List<WeakReference<Paint>> paints = new LinkedList<>();

        public static void add(Paint paint) {
            paint.setColor(mThemeColor);
            paints.add(new WeakReference<>(paint));
        }

        public static void setColor(int color) {
            Iterator<WeakReference<Paint>> iterator = paints.iterator();
            while (iterator.hasNext()) {
                Paint paint = iterator.next().get();
                if (paint != null) {
                    paint.setColor(color);
                } else {
                    iterator.remove();
                }
            }
        }
    }

    public static class ThemeColorWidgetReferenceManager {

        private static List<ThemeColorWidgetReference> widgets = new LinkedList<>();

        public static void add(ThemeColorWidgetReference widget) {
            widget.setColor(mThemeColor);
            widgets.add(widget);
        }

        public static void setColor(int color) {
            Iterator<ThemeColorWidgetReference> iterator = widgets.iterator();
            while (iterator.hasNext()) {
                ThemeColorWidgetReference widget = iterator.next();
                if (!widget.isNull()) {
                    widget.setColor(color);
                } else {
                    iterator.remove();
                }
            }
        }

    }

    public static class ThemeColorWidgetManager {

        private static List<WeakReference<ThemeColorWidget>> widgets = new LinkedList<>();

        public static void add(ThemeColorWidget widget) {
            widget.setThemeColor(mThemeColor);
            widgets.add(new WeakReference<>(widget));
        }

        public static void setColor(int color) {
            Iterator<WeakReference<ThemeColorWidget>> iterator = widgets.iterator();
            while (iterator.hasNext()) {
                ThemeColorWidget widget = iterator.next().get();
                if (widget != null) {
                    widget.setThemeColor(color);
                } else {
                    iterator.remove();
                }
            }
        }

    }

    /**
     * 初始化一个状态栏的背景色和高度，并记录这个状态栏，以后改变主题色时会将其背景色改变
     *
     * @param statusBarView 状态栏View
     */
    public static void addStatusBar(View statusBarView) {
        StatusBarManager.add(statusBarView);
    }

    /**
     * 使用主题色初始化一个标题栏，并记录这个标题栏，以后改变主题色时会将其背景色改变
     *
     * @param titleBarView 标题栏View
     */
    public static void addTitleBar(View titleBarView) {
        BackgroundManager.add(titleBarView);
    }

    public static void addToolBar(Toolbar toolbar) {
        BackgroundManager.add(toolbar);
    }

    public static void addSwitch(SwitchCompat switchCompat) {
        SwitchManager.add(switchCompat);
    }

    public static void addActivityStatusBar(Activity activity) {
        StatusBarManager.add(activity);
    }

    public static void addPreferenceCategory(ThemeColorPreferenceCategory category) {
        PreferenceCategoryManager.add(category);
    }

    public static void addPaint(Paint paint) {
        PaintManager.add(paint);
    }

    public static void addActivityNavigationBar(final Activity activity) {

        if (Build.VERSION.SDK_INT > BuildTool.Android4_4) {
            ThemeColorWidgetReferenceManager.add(new ThemeColorWidgetReference() {
                WeakReference<Activity> weakReference = new WeakReference<>(activity);

                @Override
                public void setColor(int color) {
                    activity.getWindow().setNavigationBarColor(color);
                }

                @Override
                public boolean isNull() {
                    return weakReference.get() == null;
                }
            });
        }
    }

    public static int getThemeColor() {
        return mThemeColor;
    }

    /**
     * 设置主题色并为记录的状态栏和标题栏改变颜色
     *
     * @param color 主题色RGB值
     */
    public static void setThemeColor(int color) {
        mThemeColor = color;

        BackgroundManager.setColor(mThemeColor);
        StatusBarManager.setColor(mThemeColor);
        SwitchManager.setColor(mThemeColor);
        PreferenceCategoryManager.setColor(mThemeColor);
        PaintManager.setColor(mThemeColor);
        ThemeColorWidgetManager.setColor(mThemeColor);
        ThemeColorWidgetReferenceManager.setColor(mThemeColor);
    }


    @TargetApi(21)
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build_VERSION_CODES_LOLLPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    private static final int Build_VERSION_CODES_KITKAT = 19;
    private static final int Build_VERSION_CODES_LOLLPOP = 21;

    /**
     * 根据设置一个状态栏的高度以便与系统高度一致
     *
     * @param statusBarView 状态栏View
     */
    private static void initializeStatusBarHeight(View statusBarView) {
        if (Build.VERSION.SDK_INT >= Build_VERSION_CODES_KITKAT) {
            if (statusBarView != null) {
                statusBarView.getLayoutParams().height = ViewTool
                        .getStatusBarHeight();
                statusBarView.setVisibility(View.VISIBLE);
                statusBarView.invalidate();
            }
        }
    }

    private static class NBarWidgetReference implements ThemeColorWidgetReference {

        @Override
        public void setColor(int color) {

        }

        @Override
        public boolean isNull() {
            return false;
        }
    }

}
