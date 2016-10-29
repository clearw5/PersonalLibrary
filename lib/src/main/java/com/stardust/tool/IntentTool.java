package com.stardust.tool;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.annotation.Nullable;

/**
 * Intent工具，用于Activity之间的跳转，调起其他应用程序等
 */
public class IntentTool {

    /**
     * 前往图片选择，通常会调起图库
     *
     * @param activity
     * @param requestCode 请求码，用于在回调时区分请求
     */
    public static void GotoImageSelect(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCode);
    }


    public static void goToFileSelect(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 得到一个文件选择的Uri指向文件的真实路径
     * 但貌似实测不能使用
     *
     * @param context
     * @param uri
     * @return
     */
    @Deprecated
    public static String getUriRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else {
            if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                data = uri.getPath();
            } else {
                if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                    Cursor cursor = context.getContentResolver().query(uri,
                            new String[]{ImageColumns.DATA}, null, null,
                            null);
                    if (null != cursor) {
                        if (cursor.moveToFirst()) {
                            int index = cursor
                                    .getColumnIndex(ImageColumns.DATA);
                            if (index > -1) {
                                data = cursor.getString(index);
                            }
                        }
                        cursor.close();
                    }
                }
            }
        }
        return data;
    }

    public static void goToQQ(Context context, int qq) {
        try {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void goToMail(Context context, String sendTo, @Nullable String title, @Nullable String content) {
        Uri uri = Uri.parse("mailto:" + sendTo);
        String[] email = {sendTo};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_CC, email);
        if (title != null)
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
        if (content != null)
            intent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(intent, "请选择邮件应用"));
    }

    public static void goToMail(Context context, String sendTo) {
        goToMail(context, sendTo, null, null);
    }
}
