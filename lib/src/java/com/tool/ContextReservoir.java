package com.stardust.tool;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Stardust on 2016/7/2.
 */
public class ContextReservoir {

    private static Context defaultContext;
    private static Activity defaultActivity;

    public static void setDefaultContext(Context context){
        defaultContext = context;
    }

    public static Context getDefaultContext(){
        return defaultContext;
    }

    public static void setDefaultActivity(Activity activity){
        defaultActivity = activity;
        defaultContext = activity;
    }

    public static Activity getDefaultActivity(){
        return defaultActivity;
    }
}
