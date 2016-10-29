package com.stardust.tool;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Stardust on 2016/7/2.
 */
public abstract class ConfigManager {
    protected static final String KEY_IS_FIRST_USING = "is_first_using";
    protected Map<Integer, String> integerKeyMap = new TreeMap<>();
    public Map<Integer, Integer> integerDefValueMap = new TreeMap<>();
    protected Map<Integer, String> stringKeyMap = new TreeMap<>();
    protected Map<Integer, String> booleanKeyMap = new TreeMap<>();
    protected Map<Integer, Boolean> booleanDefValueMap = new TreeMap();
    protected Activity mActivity;
    private boolean isFirstUsing;

    protected ConfigManager(Activity activity) {
        mActivity = activity;
        isFirstUsing = SharedPreferencesTool.readConfigBoolean(mActivity, KEY_IS_FIRST_USING, true);
        if (isFirstUsing)
            SharedPreferencesTool.writeConfigBoolean(mActivity, KEY_IS_FIRST_USING, false);
    }

    public int getConfigInt(int configId) {
        Integer defValue = integerDefValueMap.get(configId);
        return getConfigInt(configId, defValue == null ? 0 : defValue);
    }

    public int getConfigInt(int configId, int defValue) {
        if (!integerKeyMap.containsKey(configId))
            throw new IllegalArgumentException("config id 不能识别: config id=" + configId);
        return SharedPreferencesTool.readConfigInt(mActivity, integerKeyMap.get(configId), defValue);
    }

    public String getConfigString(int configId) {
        if (!stringKeyMap.containsKey(configId))
            throw new IllegalArgumentException("config id 不能识别: config id=" + configId);
        return SharedPreferencesTool.readConfigString(mActivity, stringKeyMap.get(configId));
    }


    public void putConfigInt(int configId, int value) {
        if (!integerKeyMap.containsKey(configId))
            throw new IllegalArgumentException("config id 不能识别: config id=" + configId);
        SharedPreferencesTool.writeConfigInt(mActivity, integerKeyMap.get(configId), value);
    }


    public void putConfigString(int configId, String value) {
        if (!stringKeyMap.containsKey(configId))
            throw new IllegalArgumentException("config id 不能识别: config id=" + configId);
        SharedPreferencesTool.writeConfigString(mActivity, stringKeyMap.get(configId), value);
    }

    public boolean getConfigBoolean(int configId) {
        if (!booleanKeyMap.containsKey(configId))
            throw new IllegalArgumentException("config id 不能识别: config id=" + configId);
        Boolean defValue = booleanDefValueMap.get(configId);
        return SharedPreferencesTool.readConfigBoolean(mActivity, booleanKeyMap.get(configId), defValue == null ? false : defValue);
    }


    public void putConfigBoolean(int configId, boolean value) {
        if (!booleanKeyMap.containsKey(configId))
            throw new IllegalArgumentException("config id 不能识别: config id=" + configId);
        SharedPreferencesTool.writeConfigBoolean(mActivity, booleanKeyMap.get(configId), value);
    }

    public boolean isFirstUsing() {
        return isFirstUsing;
    }

    public Drawable getConfigDrawable(int configId) {
        String colorOrPath = getConfigString(configId);
        if (colorOrPath != null) {
            if (colorOrPath.startsWith("#")) {
                return new ColorDrawable(Integer.parseInt(colorOrPath.substring(1)));
            }
            return ViewTool.path2Drawable(mActivity, colorOrPath);
        }
        return null;
    }

    public void putConfigDrawable(int configId, int color) {
        putConfigString(configId, "#" + color);
    }

    public void putConfigDrawable(int configId, String path) {
        putConfigString(configId, path);
    }

    public int getConfigDrawableColor(int configId, int defValue) {
        String colorOrPath = getConfigString(configId);
        if (colorOrPath != null) {
            if (colorOrPath.startsWith("#")) {
                return Integer.parseInt(colorOrPath.substring(1));
            }
        }
        return defValue;
    }

}
