package com.stardust.tool;

import java.util.Vector;

/**
 * 数组工具，提供例如分装类Vector转基本数据类型的数组
 */
public class ArrayTool {


    /**
     * Vector<Integer>转int数组
     *
     * @param integers
     * @return
     */
    public static int[] vector2intArray(Vector<Integer> integers) {
        int[] ints = new int[integers.size()];
        int i = 0;
        for (Integer integer : integers) {
            ints[i++] = integer;
        }
        return ints;
    }

    public static boolean contains(Object[] array, Object element) {
        for (Object object : array) {
            if (element.equals(object))
                return true;
        }
        return false;
    }

    public static String concatenate(String[] array, String padding) {
        StringBuilder sum = new StringBuilder();
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] != null) {
                sum.append(array[i]);
            }
        }
        if (array[array.length - 1] != null) {
            sum.append(array[array.length - 1]);
        }
        return sum.toString();
    }

    public static String concatenate(String[] array) {
        return concatenate(array, "");
    }

    public static boolean allNull(String[] array) {
        for (String str : array) {
            if (str != null)
                return false;
        }
        return true;
    }

    public static String toJavaScriptNonNullArray(String[] strings) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (String str : strings) {
            if (str != null) {
                stringBuilder.append("\"").append(str).append("\",");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}
