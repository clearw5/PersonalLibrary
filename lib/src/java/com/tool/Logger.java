package com.stardust.tool;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

/**
 * 日志记录类，将日志写到SD卡中国
 */
public class Logger {

    public static final String Path = FileTool.getLogPath() + "log.txt";
    /**
     * 日志文件最大大小
     */
    public static final int maxSize = 1024 * 1024;


    public static final int MAX_LENGTH_INFINITY = 0;
    public static final int DEFAULT_MAX_LENGTH = 500;

    public static final int DEFAULT_EXCEPTION_MAX_LENGTH = MAX_LENGTH_INFINITY;
    private static final String TAG = "Logger";

    public static void log(String str, boolean logInLogcat) {
        log(str, DEFAULT_MAX_LENGTH, true);
    }

    public static void log(String str) {
        log(str, DEFAULT_MAX_LENGTH, false);
    }

    /**
     * 将字符串写到日志中，格式为"[day:hour:minute:second]日志内容"，其中day为在全年的天数
     *
     * @param str
     */
    public static void log(String str, int maxLength, boolean logInLogcat) {
        if (logInLogcat) {
            Log.i(TAG, str);
        }
        if (maxLength > 0 && str.length() > DEFAULT_MAX_LENGTH)
            str = str.substring(0, DEFAULT_MAX_LENGTH) + "...";
        try {
            FileTool.checkFolder(FileTool.LOG_PATH);
            checkLogSize();
            System.gc();
            File file = new File(Path);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(Path, true);
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_YEAR);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            str = "[" + day + ":" + hour + ":" + minute + ":" + second + "]" + str + "\n";
            fos.write(str.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 将异常及其调用栈写到日志中
     *
     * @param exc
     */
    public static void log(Exception exc) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exc.printStackTrace(pw);
        log(sw.toString(), DEFAULT_EXCEPTION_MAX_LENGTH, false);
    }

    /**
     * 检查日志大小是否大于最大大小，如果是，把前面一半的日志删除
     */
    public static void checkLogSize() {
        try {
            File file = new File(Path);
            if (file.length() > maxSize) {
                byte[] buff = new byte[maxSize / 2];
                FileInputStream fis = new FileInputStream(Path);
                fis.skip(file.length() - maxSize / 2);
                fis.read(buff);
                fis.close();
                file.delete();
                FileOutputStream fos = new FileOutputStream(Path);
                fos.write(buff);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void println(Object object) {
        System.out.println(object);
    }
}
