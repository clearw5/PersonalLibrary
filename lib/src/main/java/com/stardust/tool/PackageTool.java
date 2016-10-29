package com.stardust.tool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

/**
 * Created by Stardust on 2016/7/23.
 */
public class PackageTool {

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        // TODO change 0 to PackageManager.sth (I cannot do that right now because of my fucking studio)
        List<PackageInfo> list = pm.getInstalledPackages(0);
        for (PackageInfo p : list) {
            if (packageName.equals(p.packageName)) {
                return true;
            }
        }
        return false;
    }


    public static boolean uninstall(Context context, String packageName) {
        try {
            Uri uri = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(uri);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean appHasUpdate(Context context, String packageName, int newVersionCode) {
        PackageManager pm = context.getPackageManager();
        // TODO fuck the android studio
        List<PackageInfo> list = pm.getInstalledPackages(0);
        for (PackageInfo p : list) {
            if (packageName.equals(p.packageName)) {
                return p.versionCode < newVersionCode;
            }
        }
        return false;
    }
}
