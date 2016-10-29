package com.stardust.tool;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Stardust on 2016/7/19.
 */
public class InputMethodTool {
    public static void showInputMethod(Context context, View view) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
        //        InputMethodManager.HIDE_IMPLICIT_ONLY);
        imm.showSoftInput(view, 0);
    }

    public static void hideInputMethod(Activity activity) {
        Logger.log("hideInputMethod");
        View focus = activity.getCurrentFocus();
        if (focus != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focus.getWindowToken(), 0);

        }
    }

    public static boolean isInputMethodVisible(Context context) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        Logger.log("isInputMethodVisible=" + imm.isActive());
        return imm.isActive();
    }

    public static boolean hideInputMethodIfNeeded(Activity activity) {
        if (isInputMethodVisible(activity)) {
            hideInputMethod(activity);
            return true;
        }
        return false;
    }
}
