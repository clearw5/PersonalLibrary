package com.stardust.text;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

/**
 * Created by Stardust on 2016/7/23.
 */
public class ClipTool {

    public static String getClip(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager manager = (ClipboardManager) context.getSystemService(
                    Context.CLIPBOARD_SERVICE);
            ClipData clipData = manager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0)
                return clipData.getItemAt(0).coerceToText(context).toString();
        } else {
            android.text.ClipboardManager manager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            return manager.getText().toString();
        }
        return null;
    }

    public static void putClip(Context context, String text) {
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText("text to clip");
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text label", "text to clip");
            clipboard.setPrimaryClip(clip);
        }
    }

}
