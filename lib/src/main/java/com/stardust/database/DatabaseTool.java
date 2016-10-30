package com.stardust.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stardust.tool.FileTool;
import com.stardust.tool.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Stardust on 2016/7/13.
 */
public class DatabaseTool {

    public static File getDbFolder(Context context) {
        return new File("data/data/" + context.getPackageName() + "/databases");
    }

    public static void checkDbFolder(Context context) {
        File dir = getDbFolder(context);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
    }

    public static boolean dbCopyFromRaw(Context context, int resId, String dbName, boolean deleteIfExists) {
        checkDbFolder(context);
        File file = new File(getDbFolder(context), dbName + ".db");
        if (file.exists()) {
            if (deleteIfExists) {
                if (!file.delete()) {
                    Logger.log("file delete fail");
                    return false;
                }
            } else {
                return false;
            }
        }
        try {
            file.createNewFile();
            FileOutputStream target = new FileOutputStream(file);
            InputStream source = context.getResources().openRawResource(resId);
            return FileTool.fileCopyAnyway(source, target);
        } catch (Exception exc) {
            exc.printStackTrace();
            Logger.log(exc);
        }
        return false;
    }

    public static void printAll(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select * from " + tableName, null);
        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    System.out.println(cursor.getColumnName(i) + ":" + read(cursor, i));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public static String read(Cursor cursor, int index) {
        int type = CursorCompat.getType(cursor, index);
        switch (type) {
            case Cursor.FIELD_TYPE_STRING:
                return cursor.getString(index);
            case Cursor.FIELD_TYPE_INTEGER:
                return String.valueOf(cursor.getInt(index));
        }
        return "";
    }

}
