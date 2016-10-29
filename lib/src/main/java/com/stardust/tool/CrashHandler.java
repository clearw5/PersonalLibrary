package com.stardust.tool;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Stardust on 2016/10/25.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private final Class<?> mErrorReportClass;

    public CrashHandler(Class<?> errorReportClass){
        mErrorReportClass = errorReportClass;
    }



    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
        try {
            Log.e(TAG, "Uncaught Exception!!!");
            ex.printStackTrace();
            final String msg = "很抱歉，程序遇到未知错误，即将停止运行\n错误代码：" + ex.toString();
            final Activity activity = ContextReservoir.getDefaultActivity();
            if (activity == null) {
                System.exit(0);
                return;
            } else {
                Intent intent = new Intent(activity, mErrorReportClass);
                intent.putExtra("message", msg);
                intent.putExtra("error", throwableToString(ex));
                activity.startActivity(intent);
            }
            System.exit(0);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static String throwableToString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}