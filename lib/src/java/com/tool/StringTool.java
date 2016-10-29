package com.stardust.tool;

import com.stardust.calculator.naturalview.NaturalView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一些字符串工具
 */
public class StringTool {
    /**
     * 以下表示字符的类型
     */
    /**
     * 小数（数字0~9、小数点）
     */
    public static final int DECIMAL = 0x1;
    /**
     * 数字0~9
     */
    public static final int DIGIT = 0x10;
    /**
     * 字母
     */
    public static final int LETTER = 0x100;
    /**
     * 常量
     */
    public static final int CONSTANT = 0X1000;
    /**
     * 变量，包括字母和常量字符
     */
    public static final int VARIABLE = 0x1100;
    /**
     * 其他
     */
    public static final int OTHERS = 0;
    /**
     * 常量
     */
    public static final char[] Constants = {'π'};

    /**
     * 忽略StringIndexOufLength异常的子字符串
     * 让end过大超出字符串长度时返回从start到字符串结束的字串
     * 其他超出范围的情况返回空字符串
     *
     * @param str   原字符串
     * @param start 起始位置
     * @param end   结束位置（取不到）
     * @return String 子串
     */
    public static String substring(String str, int start, int end) {
        if (start >= end || start < 0 || start >= str.length())
            return "";
        else if (end > str.length())
            return str.substring(start);
        else
            return str.substring(start, end);
    }

    /**
     * 判断字符是否是字母
     *
     * @param c
     * @return
     */
    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    /**
     * 向右寻找左括号对应的右括号
     *
     * @param str
     * @param start 开始寻找的位置（包括该位置），也即左括号位置为start-1
     * @return 右括号位置，找不到时返回-1
     */
    public static int findRightParenthesis(String str, int start) {
        int n = 0;
        for (int k = start; k < str.length(); k++) {
            if (str.charAt(k) == '(')
                n++;
            else if (str.charAt(k) == ')') {
                if (n > 0)
                    n--;
                else {
                    return k;
                }
            }
        }
        return -1;
    }

    /**
     * 向左寻找右括号对应的左括号
     *
     * @param str
     * @param end 开始寻找的位置（包括该位置），也即右括号位置为end+1
     * @return 左括号位置, 找不到时返回-1
     */
    public static int findLeftParenthesis(String str, int end) {
        int n = 0;
        for (int k = end; k >= 0; k--) {
            if (str.charAt(k) == ')')
                n++;
            else if (str.charAt(k) == '(') {
                if (n > 0)
                    n--;
                else {
                    return k;
                }
            }
        }
        return -1;
    }

    /**
     * 向左寻找右方括号对应的左方括号
     *
     * @param str
     * @param end 开始寻找的位置（包括该位置），也即右方括号位置为end+1
     * @return 左方括号位置, 找不到时返回-1
     */
    public static int findLeftSquareBracket(String str, int end) {
        int n = 0;
        for (int k = end; k >= 0; k--) {
            if (str.charAt(k) == NaturalView.LEFT_PARENTHESIS_CHAR)
                n++;
            else if (str.charAt(k) == NaturalView.RIGHT_PARENTHESIS_CHAR) {
                if (n > 0)
                    n--;
                else {
                    return k;
                }
            }
        }
        return -1;
    }


    /**
     * 得到字符的类型
     *
     * @param ch
     * @return
     */
    public static int getType(char ch) {
        int type = OTHERS;
        if (isDigit(ch))
            type |= DIGIT;
        if (ch == '.')
            type |= DECIMAL;
        if (isLetter(ch))
            type |= LETTER;
        for (int i = 0; i < Constants.length; i++)
            if (ch == Constants[i]) {
                type |= CONSTANT;
                break;
            }
        return type;
    }

    /**
     * 判断字符是否是数字 0~9
     *
     * @param ch
     * @return
     */
    public static boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }

    /**
     * 统计某个字符在字符串中出现的个数
     *
     * @param str
     * @param ch
     * @return 字符ch在str中的数量
     */
    public static int countChar(String str, char ch) {
        int count = 0;
        int index = str.indexOf(ch);
        while (index > 0) {
            index = str.indexOf(ch, index + 1);
            count++;
        }
        return count;
    }

    public static boolean allIsLetter(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static void clear(StringBuffer sb) {
        if (sb.length() > 0)
            sb.delete(0, sb.length());
    }


    public static String trim(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str.length() >= prefix.length()) {
            return str.substring(0, prefix.length()).equalsIgnoreCase(prefix);
        }
        return false;
    }

    public static boolean containsChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            int code = str.codePointAt(i);
            if (code >= 0x4e00 && code <= 0x9fbb)
                return true;
        }
        return false;
    }
}
