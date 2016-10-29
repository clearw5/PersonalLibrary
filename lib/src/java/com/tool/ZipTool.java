package com.stardust.tool;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Stardust on 2016/7/20.
 */
public class ZipTool {

    public static boolean unzip(File file, String folderPath) {
        try {
            ZipFile zipfile = new ZipFile(file);
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipfile.entries();
            ZipEntry entry;
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                FileTool.mkdirs(entry.getName());
                if (entry.isDirectory()) {
                    continue;
                }
                InputStream is = new BufferedInputStream(zipfile.getInputStream(entry));
                if (!FileTool.fileDeleteAndCopy(is, folderPath + entry.getName()))
                    return false;
            }
            zipfile.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
