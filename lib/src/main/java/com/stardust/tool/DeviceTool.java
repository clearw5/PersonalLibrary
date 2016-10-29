package com.stardust.tool;

import android.os.Build;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Stardust on 2016/5/14.
 * <p/>
 * 设备工具，主要是获取设备信息（包括获取CPU信息）
 */
public class DeviceTool {
    private static String[] mArmArchitecture;

    /**
     * 获取cpu类型和架构
     *
     * @return 三个String组成的数组，第一个参数标识ARM和INTEL架构(其他架构则为null)，第二个参数标识是V6还是V7架构(数字6或者7，其他则为null），第三个参数标识neon或atom指令集(其他则为null)
     */
    public static String[] getCpuArchitecture() {
        if (mArmArchitecture != null) {
            return mArmArchitecture;
        }
        mArmArchitecture = new String[3];
        try {
            //通过读取cpuinfo文件获取cpu信息
            InputStream is = new FileInputStream("/proc/cpuinfo");
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String nameProcessor = "Processor";
                String nameFeatures = "Features";
                String nameModel = "model name";
                String nameCpuFamily = "cpu family";
                while (true) {
                    String line = bufferedReader.readLine();
                    String[] pair = null;
                    if (line == null) {
                        break;
                    }
                    pair = line.split(":");
                    if (pair.length != 2)
                        continue;
                    String key = pair[0].trim();
                    String val = pair[1].trim();
                    if (key.compareTo(nameProcessor) == 0) {
                        String n = "";
                        for (int i = val.indexOf("ARMv") + 4; i < val.length(); i++) {
                            String temp = val.charAt(i) + "";
                            if (temp.matches("\\d")) {
                                n += temp;
                            } else {
                                break;
                            }
                        }
                        mArmArchitecture[0] = "ARM";
                        mArmArchitecture[1] = n;
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameFeatures) == 0) {
                        if (val.contains("neon")) {
                            mArmArchitecture[2] = "neon";
                        }
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameModel) == 0) {
                        if (val.contains("Intel")) {
                            mArmArchitecture[0] = "INTEL";
                            mArmArchitecture[2] = "atom";
                        }
                        continue;
                    }

                    if (key.compareToIgnoreCase(nameCpuFamily) == 0) {
                        mArmArchitecture[1] = val;
                        continue;
                    }
                }
            } finally {
                bufferedReader.close();
                inputStreamReader.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.i("cpu", mArmArchitecture[0] + "v" + mArmArchitecture[1] + "," + mArmArchitecture[2]);
        //Logger.log(mArmArchitecture[0] + "v" + mArmArchitecture[1] + "," + mArmArchitecture[2]);
        return mArmArchitecture;
    }

    /**
     * 判断当前设备是否是genymotion模拟器
     *
     * @return
     */
    public static boolean isGenymotionVirtualPhone() {
        return Build.MODEL.startsWith("genymotion");
    }
}
