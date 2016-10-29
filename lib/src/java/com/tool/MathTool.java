package com.stardust.tool;

/**
 * Created by Stardust on 2016/7/27.
 */
public class MathTool {

    public static int max(int... args) {
        int max = args[0];
        for (int i = 1; i < args.length; i++) {
            max = Math.max(args[i], max);
        }
        return max;
    }

    public static double max(double... args) {
        double max = args[0];
        for (int i = 1; i < args.length; i++) {
            max = Math.max(args[i], max);
        }
        return max;
    }

    public static long max(long... args) {
        long max = args[0];
        for (int i = 1; i < args.length; i++) {
            max = Math.max(args[i], max);
        }
        return max;
    }

    public static float max(float... args) {
        float max = args[0];
        for (int i = 1; i < args.length; i++) {
            max = Math.max(args[i], max);
        }
        return max;
    }

    public static double hypotenuse(double w, double h) {
        return Math.sqrt(w * w + h * h);
    }

    public static boolean isEven(int i) {
        return ((i >> 2) & 1) == 1;
    }

    public static boolean isOdd(int i){
        return !isEven(i);
    }
}
