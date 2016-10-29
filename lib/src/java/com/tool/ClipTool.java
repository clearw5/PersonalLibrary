package com.stardust.tool;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Stardust on 2016/7/23.
 */
public class ClipTool {

    public static String getClip(Context context) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        ClipData clipData = manager.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0)
            return clipData.getItemAt(0).coerceToText(context).toString();
        return null;
    }

    public static void putClip(Context context, String text) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("ClipTool", text));
    }
}
