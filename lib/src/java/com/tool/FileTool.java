package com.stardust.tool;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具类，用于将文件保存在SD卡的本应用的目录中，以及文件复制等
 */
public class FileTool {

    /**
     * 本程序在SD卡中的默认储存路径
     */
    public static final String mPath = "/星尘计算器/";
    public static final String TMP_PATH = "tmp/";
    public static final String RES_PATH = "res/";
    public static final String LOG_PATH = "log/";

    /**
     * 文件读写默认缓冲区大小
     */
    public static final int DefaultBufferSize = 100 * 1024;

    /**
     * 将输入流is中的内容保存到SD卡本应用目录下的path路径下
     * 如果文件已经存在则覆盖
     * 如果文件路径包含文件夹，则自动创建
     *
     * @param is
     * @param path 相对路径，例如path为"config.xml"则保存到mPath/config.xml
     */
    public static boolean saveFile(InputStream is, String path) {
        checkFolder();
        mkdirs(path);
        return fileDeleteAndCopy(is, getPath() + path);
    }

    //检查SD卡本应用目录文件夹是否存在
    public static void checkFolder() {
        createFileIfNotExists(getPath());
        createFileIfNotExists(getTmpPath());
    }

    public static void createFileIfNotExists(String path) {
        File file = new File(path);
        if (!file.exists())
            if (path.endsWith("\\") || path.endsWith("/")) {
                file.mkdirs();
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    /**
     * 检查某个文件路径所依赖的文件夹是否存在，不存在则创建
     *
     * @param path 例如"/a/b/c.txt",将检查"/a/b/"文件夹是否存在，不存在则创建
     */
    public static void mkdirs(String path) {
        int index = path.lastIndexOf('\\');
        if (index < 0)
            index = path.lastIndexOf('/');
        if (index >= 0) {
            File folder = new File(path.substring(0, index + 1));
            folder.mkdirs();
        }
    }

    /**
     * 得到本程序在SD卡中的对应目录路径
     *
     * @return SD卡路径+mPath
     */
    public static String getPath() {
        return Environment.getExternalStorageDirectory().getPath() + mPath;
    }

    public static String getTmpPath() {
        return getPath() + TMP_PATH;
    }

    public static String getResPath() {
        return getPath() + RES_PATH;
    }

    /**
     * 文件复制
     *
     * @param is 文件源输入流
     * @param os 目标位置输出流
     */
    public static boolean fileCopyAnyway(InputStream is, OutputStream os) {
        try {
            byte[] buffer = new byte[DefaultBufferSize];
            while (is.available() > 0) {
                if (is.available() < DefaultBufferSize) {
                    int len = is.available();
                    is.read(buffer, 0, len);
                    os.write(buffer, 0, len);
                } else {
                    is.read(buffer);
                    os.write(buffer);
                }
            }
            os.close();
            is.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log(e);
            return false;
        }
    }

    public static boolean fileDeleteAndCopy(InputStream is, String path) {
        mkdirs(path);
        File file = new File(path);
        if (file.exists()) {
            if (!file.delete())
                return false;
        }
        try {
            file.createNewFile();
            return fileCopyAnyway(is, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean write(String path, String data) {
        try {
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes("UTF-8"));
            fos.close();
            return true;
        } catch (Exception exc) {
            exc.printStackTrace();
            Logger.log(exc);
            return false;
        }
    }

    public static boolean writeSdCard(String path, String data) {
        return write(Environment.getExternalStorageDirectory().getPath() + "/" + path, data);
    }

    public static boolean writeDataFolder(String path, String data) {
        return write(getPath() + path, data);
    }

    public static void deleteFile(String path, final String suffix) {
        File file = new File(getPath() + path);
        if (file.exists()) {
            String[] fileNames = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(suffix);
                }
            });
            for (String fileName : fileNames) {
                (new File(getPath() + path + fileName)).delete();
            }
            Logger.log("deleted " + fileNames.length + " file(s)");
        } else {
            Logger.log("tmp folder does not exist");
        }
    }

    public static String generateFileName(String suffix) {
        checkFolder();
        int n = 0;
        File file = new File(getPath() + n + suffix);
        while (file.exists()) {
            n++;
            file = new File(getPath() + n + suffix);
        }
        return n + suffix;
    }

    public static String generateFileName(String prefix, String suffix) {
        prefix = prefix.replace('/', '_');
        checkFolder();
        int n = 0;
        String name = prefix + suffix;
        File file = new File(getPath() + name);
        while (file.exists()) {
            n++;
            name = prefix + "(" + n + ")" + suffix;
            file = new File(getPath() + name);
        }
        return name;
    }

    public static String getLogPath() {
        return getPath() + LOG_PATH;
    }

    public static void checkFolder(String path) {
        mkdirs(getPath() + path);
    }
}
