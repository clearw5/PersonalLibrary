package com.stardust.tool;

/**
 * Created by Stardust on 2016/10/29.
 */
public class StringTool {
    public static int compareIgnoreCase(String lhs, String rhs) {
        if (lhs == null) {
            return rhs == null ? 0 : -1;
        }
        if (rhs == null)
            return 1;
        return lhs.compareToIgnoreCase(rhs);
    }
}
