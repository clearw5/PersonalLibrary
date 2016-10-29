package com.stardust.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 配置保存工具，提供读取配置，写入配置等方法，简化了SharedPreferences的使用
 */
public class SharedPreferencesTool {

    /**
     * 默认的配置文件名
     */
    public static final String CONFIG_SP_NAME = "config";

    /**
     * 从配置中读取一个int值
     *
     * @param activity
     * @param spName       配置名称
     * @param key          变量值关键字（变量名）
     * @param defaultValue 默认值，当配置中没有这个变量时返回这个值
     * @return
     */
    public static int readInt(Context activity, String spName, String key,
                              int defaultValue) {
        SharedPreferences sp = activity.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 在配置中写入一个int值
     *
     * @param activity
     * @param spName   配置名称
     * @param key      变量值关键字（变量名）
     * @param value    变量值
     */
    public static void writeInt(Context activity, String spName, String key, int value) {
        SharedPreferences sp = activity.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 从默认配置中读取一个int值
     *
     * @param activity
     * @param key          变量值关键字（变量名）
     * @param defaultValue 默认值，当配置中没有这个变量时返回这个值
     * @return
     */
    public static int readConfigInt(Context activity, String key,
                                    int defaultValue) {
        return readInt(activity, CONFIG_SP_NAME, key, defaultValue);
    }

    /**
     * 在默认配置中写入一个int值
     *
     * @param activity
     * @param key      变量值关键字（变量名）
     * @param value    变量值
     */
    public static void writeConfigInt(Context activity, String key,
                                      int value) {
        writeInt(activity, CONFIG_SP_NAME, key, value);
    }

    /**
     * 从默认配置中读取一个String值
     *
     * @param activity
     * @param key      变量值关键字（变量名）
     * @return 当配置中没有这个变量时返回null
     */
    static String readString(Context activity, String spName, String key) {
        SharedPreferences sp = activity.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    /**
     * 在配置中写入一个String值
     *
     * @param activity
     * @param spName   配置名称
     * @param key      变量值关键字（变量名）
     * @param value    变量值
     */
    public static void writeString(Context activity, String spName,
                                   String key, String value) {
        SharedPreferences sp = activity.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 从配置中读取一个String值
     *
     * @param activity
     * @param key      变量值关键字（变量名）
     * @return 当配置中没有这个变量时返回null
     */
    public static String readConfigString(Context activity, String key) {
        return readString(activity, CONFIG_SP_NAME, key);
    }

    /**
     * 在默认配置中写入一个String值
     *
     * @param activity
     * @param key      变量值关键字（变量名）
     * @param value    变量值
     */
    public static void writeConfigString(Context activity, String key,
                                         String value) {
        writeString(activity, CONFIG_SP_NAME, key, value);
    }

    /**
     * 从配置中读取一个float值
     *
     * @param activity
     * @param spName       配置名称
     * @param key          变量值关键字（变量名）
     * @param defaultValue 默认值，当配置中没有这个变量时返回这个值
     * @return
     */
    public static float readFloat(Context activity, String spName, String key,
                                  float defaultValue) {
        SharedPreferences sp = activity.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    /**
     * 在配置中写入一个float值
     *
     * @param activity
     * @param spName   配置名称
     * @param key      变量值关键字（变量名）
     * @param value    变量值
     */
    public static void writeFloat(Context activity, String spName, String key, float value) {
        SharedPreferences sp = activity.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 从默认配置中读取一个boolean值
     *
     * @param activity
     * @param key          变量值关键字（变量名）
     * @param defaultValue 默认值，当配置中没有这个变量时返回这个值
     * @return
     */
    public static boolean readConfigBoolean(Context activity, String key, boolean defaultValue) {
        SharedPreferences sp = activity.getSharedPreferences(CONFIG_SP_NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 在默认配置中写入一个boolean值
     *
     * @param context
     * @param key      变量值关键字（变量名）
     * @param value    变量值
     */
    public static void writeConfigBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG_SP_NAME,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean readBoolean(Context context, String spName, String key,
                                      boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void writeBoolean(Context context, String spName, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(spName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
