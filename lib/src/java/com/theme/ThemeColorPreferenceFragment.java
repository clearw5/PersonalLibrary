package com.stardust.theme;

import android.preference.PreferenceFragment;
import android.widget.ListView;

import java.lang.reflect.Field;

/**
 * Created by Stardust on 2016/8/14.
 */
public class ThemeColorPreferenceFragment extends PreferenceFragment implements ThemeColorManager.ThemeColorWidget {

    private static Field LIST_VIEW;
    private int mThemeColor;
    private boolean hasApplyThemeColor = false;

    static {
        try {
            LIST_VIEW = PreferenceFragment.class.getDeclaredField("mList");
            LIST_VIEW.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
            LIST_VIEW = null;
        }
    }


    private ListView getListView() {
        try {
            return (ListView) LIST_VIEW.get(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPreferencesFromResource(int resId) {
        super.addPreferencesFromResource(resId);
        ThemeColorManager.ThemeColorWidgetManager.add(this);
    }

    @Override
    public void setThemeColor(int color) {
        if (mThemeColor == color)
            return;
        mThemeColor = color;
        hasApplyThemeColor = false;
    }

    private void applyThemeColor() {
        ListView listView = getListView();
        if (listView != null) {
            ScrollingViewThemeColorManager.setEdgeGlowColor(listView, mThemeColor);
            hasApplyThemeColor = true;
        }
    }


    public void onResume() {
        super.onResume();
        if (!hasApplyThemeColor) {
            applyThemeColor();
        }
    }

}
